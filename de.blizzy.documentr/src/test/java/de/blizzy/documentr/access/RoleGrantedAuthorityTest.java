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
package de.blizzy.documentr.access;

import static de.blizzy.documentr.TestUtil.*;
import static org.junit.Assert.*;

import org.junit.Test;

import de.blizzy.documentr.access.GrantedAuthorityTarget.Type;

public class RoleGrantedAuthorityTest {
	@Test
	public void getTarget() {
		GrantedAuthorityTarget target = new GrantedAuthorityTarget("project", Type.PROJECT); //$NON-NLS-1$
		RoleGrantedAuthority authority = new RoleGrantedAuthority(target, "admin"); //$NON-NLS-1$
		assertEquals(target, authority.getTarget());
	}

	@Test
	public void getRoleName() {
		GrantedAuthorityTarget target = new GrantedAuthorityTarget("project", Type.PROJECT); //$NON-NLS-1$
		RoleGrantedAuthority authority = new RoleGrantedAuthority(target, "admin"); //$NON-NLS-1$
		assertEquals("admin", authority.getRoleName()); //$NON-NLS-1$
	}
	
	@Test
	public void testEquals() {
		assertEqualsContract(
				new RoleGrantedAuthority(
						new GrantedAuthorityTarget("project", Type.PROJECT), //$NON-NLS-1$
						"admin"), //$NON-NLS-1$
				new RoleGrantedAuthority(
						new GrantedAuthorityTarget("project", Type.PROJECT), //$NON-NLS-1$
						"admin"), //$NON-NLS-1$
				new RoleGrantedAuthority(
						new GrantedAuthorityTarget("project", Type.PROJECT), //$NON-NLS-1$
						"admin"), //$NON-NLS-1$
				new RoleGrantedAuthority(
						new GrantedAuthorityTarget("project2", Type.PROJECT), //$NON-NLS-1$
						"admin")); //$NON-NLS-1$
	}

	@Test
	public void testHashCode() {
		assertHashCodeContract(
				new RoleGrantedAuthority(
						new GrantedAuthorityTarget("project", Type.PROJECT), //$NON-NLS-1$
						"admin"), //$NON-NLS-1$
				new RoleGrantedAuthority(
						new GrantedAuthorityTarget("project", Type.PROJECT), //$NON-NLS-1$
						"admin")); //$NON-NLS-1$
	}
}