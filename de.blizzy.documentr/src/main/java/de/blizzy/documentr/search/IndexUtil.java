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
package de.blizzy.documentr.search;

import java.io.IOException;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;

final class IndexUtil {
	private IndexUtil() {}

	static void closeQuietly(IndexWriter writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				// ignore
			} catch (RuntimeException e) {
				// ignore
			}
		}
	}

	static void closeQuietly(IndexReader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				// ignore
			} catch (RuntimeException e) {
				// ignore
			}
		}
	}

	static void closeQuietly(IndexSearcher searcher) {
		if (searcher != null) {
			try {
				searcher.close();
			} catch (IOException e) {
				// ignore
			} catch (RuntimeException e) {
				// ignore
			}
		}
	}

	static void closeQuietly(TokenStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				// ignore
			} catch (RuntimeException e) {
				// ignore
			}
		}
	}

	static void closeQuietly(Directory directory) {
		if (directory != null) {
			try {
				directory.close();
			} catch (IOException e) {
				// ignore
			} catch (RuntimeException e) {
				// ignore
			}
		}
	}
}