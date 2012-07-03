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
package de.blizzy.documentr.web.search;

import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.blizzy.documentr.access.DocumentrAnonymousAuthenticationFactory;
import de.blizzy.documentr.search.PageIndex;
import de.blizzy.documentr.search.SearchResult;

@Controller
@RequestMapping("/search")
public class SearchController {
	@Autowired
	private PageIndex pageIndex;
	@Autowired
	private DocumentrAnonymousAuthenticationFactory authenticationFactory;
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	@PreAuthorize("permitAll")
	public String findPages(@RequestParam("q") String searchText, @RequestParam(value="p", required=false) Integer page,
			Authentication authentication, Model model) throws IOException {
		
		if (page == null) {
			page = Integer.valueOf(1);
		}

		// TODO: why can authentication be null here?
		if (authentication == null) {
			authentication = authenticationFactory.create("dummy"); //$NON-NLS-1$
		}
		
		try {
			SearchResult result = pageIndex.findPages(searchText, page.intValue(), authentication);
			model.addAttribute("searchText", searchText); //$NON-NLS-1$
			model.addAttribute("searchResult", result); //$NON-NLS-1$
			model.addAttribute("page", page); //$NON-NLS-1$
		} catch (ParseException e) {
			// TODO
		}
		return "/search/result"; //$NON-NLS-1$
	}
}