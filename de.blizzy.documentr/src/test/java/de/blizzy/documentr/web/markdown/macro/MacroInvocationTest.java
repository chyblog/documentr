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
package de.blizzy.documentr.web.markdown.macro;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class MacroInvocationTest {
	@Test
	public void getMacro() {
		IMacro macro = mock(IMacro.class);
		MacroInvocation invocation = new MacroInvocation(macro);
		assertSame(macro, invocation.getMacro());
	}

	@Test
	public void getMarker() {
		IMacro macro = mock(IMacro.class);
		MacroInvocation invocation = new MacroInvocation(macro);
		assertTrue(StringUtils.isNotBlank(invocation.getMarker()));
	}
}
