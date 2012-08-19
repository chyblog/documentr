/*
documentr - Edit, maintain, and present software documentation on the web.
Copyright (C) 2012 Maik Schreiber

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package de.blizzy.documentr.repository;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;

@Component
public class LockManager {
	private Map<LockKey, Lock> locks = Maps.newHashMap();
	private Lock allLock;

	ILock lockAll() {
		return lock(new LockKey(null, null, false), true);
	}
	
	ILock lockProjectCentral(String projectName) {
		Assert.hasLength(projectName);
		
		return lock(new LockKey(projectName, null, true), false);
	}

	ILock lockProjectBranch(String projectName, String branchName) {
		Assert.hasLength(projectName);
		Assert.hasLength(branchName);
		
		return lock(new LockKey(projectName, branchName, false), false);
	}

	private ILock lock(LockKey key, boolean all) {
		Thread thread = Thread.currentThread();
		Lock lock;
		synchronized (locks) {
			if (all) {
				for (;;) {
					lock = allLock;
					if (((lock == null) || (lock.getLockingThread() == thread)) &&
						locks.isEmpty()) {
						
						break;
					}

					try {
						locks.wait();
					} catch (InterruptedException e) {
						// ignore
					}
				}
				
				if (lock == null) {
					lock = new Lock(thread);
					allLock = lock;
				}
			} else {
				for (;;) {
					lock = locks.get(key);
					if ((allLock == null) &&
						((lock == null) || (lock.getLockingThread() == thread))) {
						
						break;
					}
					
					try {
						locks.wait();
					} catch (InterruptedException e) {
						// ignore
					}
				}

				if (lock == null) {
					lock = new Lock(thread);
					locks.put(key, lock);
				}
			}

			lock.increaseUseCount();
		}
		return lock;
	}
	
	void unlock(ILock lock) {
		Assert.notNull(lock);
		Assert.isInstanceOf(Lock.class, lock);
		
		Lock l = (Lock) lock;

		if (l.getLockingThread() != Thread.currentThread()) {
			throw new IllegalStateException("current thread is not lock owner"); //$NON-NLS-1$
		}

		synchronized (locks) {
			boolean isRegular = locks.values().contains(l);
			boolean isAll = l == allLock;
			
			if (!isRegular && !isAll) {
				throw new IllegalStateException("unknown lock"); //$NON-NLS-1$
			}
			
			int newUseCount = l.decreaseUseCount();
			if (newUseCount == 0) {
				if (isRegular) {
					locks.values().remove(l);
				} else {
					allLock = null;
				}
			}
			
			locks.notifyAll();
		}
	}
}
