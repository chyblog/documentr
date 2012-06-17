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
package de.blizzy.documentr.web.markdown.macro.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.internal.matchers.Equals;
import org.mockito.internal.matchers.Not;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.Authentication;

import com.google.common.collect.Lists;

import de.blizzy.documentr.DocumentrConstants;
import de.blizzy.documentr.access.DocumentrPermissionEvaluator;
import de.blizzy.documentr.access.Permission;
import de.blizzy.documentr.page.IPageStore;
import de.blizzy.documentr.page.Page;
import de.blizzy.documentr.web.markdown.HtmlSerializerContext;
import de.blizzy.documentr.web.markdown.macro.IMacroContext;

public class NeighborsMacroTest {
	private static final String PROJECT = "project"; //$NON-NLS-1$
	private static final String BRANCH = "branch"; //$NON-NLS-1$
	private static final String INACCESSIBLE_PAGE_PATH = DocumentrConstants.HOME_PAGE_NAME + "/foo/inaccessible"; //$NON-NLS-1$
	@SuppressWarnings("nls")
	private static final String[] PAGES = {
		DocumentrConstants.HOME_PAGE_NAME,
		DocumentrConstants.HOME_PAGE_NAME + "/foo",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/aaa",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/aaa/a1",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/aaa/a2",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/bar", // <-- page under test
		DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/x",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/x/x1",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/x/x2",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/y",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/y/y1",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/y/y2",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/z",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/z/z1",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/z/z2",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/bbb",
		DocumentrConstants.HOME_PAGE_NAME + "/foo/bbb/b1",
		INACCESSIBLE_PAGE_PATH
	};
	
	private IPageStore pageStore;
	private HtmlSerializerContext htmlSerializerContext;
	private IMacroContext macroContext;
	private DocumentrPermissionEvaluator permissionEvaluator;

	@Before
	public void setUp() throws IOException {
		htmlSerializerContext = mock(HtmlSerializerContext.class);
		when(htmlSerializerContext.getPageURI(anyString())).then(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				return "/" + invocation.getArguments()[0]; //$NON-NLS-1$
			}
		});
		
		pageStore = mock(IPageStore.class);
		setupPages();

		permissionEvaluator = mock(DocumentrPermissionEvaluator.class);
		setupPagePermissions();
		
		macroContext = mock(IMacroContext.class);
		when(macroContext.getPageStore()).thenReturn(pageStore);
		when(macroContext.getPermissionEvaluator()).thenReturn(permissionEvaluator);
	}
	
	private void setupPages() throws IOException {
		setupPages(PAGES);
		
		for (String page : PAGES) {
			List<String> childPages = Lists.newArrayList();
			String childPagePrefix = page + "/"; //$NON-NLS-1$
			for (String childPage : PAGES) {
				if (childPage.startsWith(childPagePrefix)) {
					String rest = StringUtils.substringAfter(childPage, childPagePrefix);
					if (!rest.contains("/")) { //$NON-NLS-1$
						childPages.add(childPage);
					}
				}
			}
			Collections.sort(childPages);
			when(pageStore.listChildPagePaths(PROJECT, BRANCH, page)).thenReturn(childPages);
		}
	}
	
	@SuppressWarnings({ "boxing", "unchecked" })
	private void setupPagePermissions() {
		when(permissionEvaluator.hasPagePermission(Matchers.<Authentication>any(),
				eq(PROJECT), eq(BRANCH), Matchers.<String>argThat(new Not(new Equals(INACCESSIBLE_PAGE_PATH))),
				same(Permission.VIEW)))
				.thenReturn(true);
		when(permissionEvaluator.hasPagePermission(Matchers.<Authentication>any(),
				eq(PROJECT), eq(BRANCH), eq(INACCESSIBLE_PAGE_PATH),
				same(Permission.VIEW)))
				.thenReturn(false);
	}
	
	@Test
	public void getHtml() {
		when(htmlSerializerContext.getProjectName()).thenReturn(PROJECT);
		when(htmlSerializerContext.getBranchName()).thenReturn(BRANCH);
		when(htmlSerializerContext.getPagePath()).thenReturn(DocumentrConstants.HOME_PAGE_NAME + "/foo/bar"); //$NON-NLS-1$
		
		NeighborsMacro macro = new NeighborsMacro();
		macro.setHtmlSerializerContext(htmlSerializerContext);
		macro.setMacroContext(macroContext);
		
		// this is the HTML for home/foo/bar
		@SuppressWarnings("nls")
		String html =
				"<div class=\"neighbors-box\">" +
					"<ul class=\"neighbors\">" +
						"<li>" +
							"<a href=\"/" + DocumentrConstants.HOME_PAGE_NAME + "\">" + DocumentrConstants.HOME_PAGE_NAME + "</a>" +
							"<ul>" +
								"<li>" +
									"<a href=\"/" + DocumentrConstants.HOME_PAGE_NAME + "/foo\">" + DocumentrConstants.HOME_PAGE_NAME + "/foo</a>" +
									"<ul>" +
										"<li><a href=\"/" + DocumentrConstants.HOME_PAGE_NAME + "/foo/aaa\">" + DocumentrConstants.HOME_PAGE_NAME + "/foo/aaa</a></li>" +
										"<li class=\"active\">" +
											DocumentrConstants.HOME_PAGE_NAME + "/foo/bar" +
											"<ul>" +
												"<li><a href=\"/" + DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/x\">" + DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/x</a></li>" +
												"<li><a href=\"/" + DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/y\">" + DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/y</a></li>" +
												"<li><a href=\"/" + DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/z\">" + DocumentrConstants.HOME_PAGE_NAME + "/foo/bar/z</a></li>" +
											"</ul>" +
										"</li>" +
										"<li><a href=\"/" + DocumentrConstants.HOME_PAGE_NAME + "/foo/bbb\">" + DocumentrConstants.HOME_PAGE_NAME + "/foo/bbb</a></li>" +
									"</ul>" +
								"</li>" +
							"</ul>" +
						"</li>" +
					"</ul>" +
				"</div>";
		assertEquals(html, macro.getHtml());
	}

	private void setupPages(String... pagePaths) throws IOException {
		for (String pagePath : pagePaths) {
			setupPage(pagePath);
		}
	}

	private void setupPage(String pagePath) throws IOException {
		String parentPagePath = pagePath.contains("/") ? //$NON-NLS-1$
				StringUtils.substringBeforeLast(pagePath, "/") : //$NON-NLS-1$
				null;
		Page page = Page.fromText(pagePath, "text"); //$NON-NLS-1$
		page.setParentPagePath(parentPagePath);
		when(pageStore.getPage(PROJECT, BRANCH, pagePath, false)).thenReturn(page);
	}
}
