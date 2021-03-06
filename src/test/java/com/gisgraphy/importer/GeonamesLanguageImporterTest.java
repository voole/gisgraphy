/*******************************************************************************
 *   Gisgraphy Project 
 * 
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 * 
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *   Lesser General Public License for more details.
 * 
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA
 * 
 *  Copyright 2008  Gisgraphy project 
 *  David Masclet <davidmasclet@gisgraphy.com>
 *  
 *  
 *******************************************************************************/
package com.gisgraphy.importer;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.easymock.EasyMock;
import org.junit.Test;

import com.gisgraphy.domain.repository.CountryDao;
import com.gisgraphy.domain.repository.ILanguageDao;
import com.gisgraphy.domain.repository.LanguageDao;
import com.gisgraphy.domain.valueobject.NameValueDTO;

public class GeonamesLanguageImporterTest {

    @Test
    public void rollbackShouldRollback() {
	GeonamesLanguageImporter geonamesLanguageImporter = new GeonamesLanguageImporter();
	ILanguageDao languageDao = EasyMock.createMock(ILanguageDao.class);
	EasyMock.expect(languageDao.deleteAll()).andReturn(4);
	EasyMock.replay(languageDao);
	geonamesLanguageImporter.setLanguageDao(languageDao);
	List<NameValueDTO<Integer>> deleted = geonamesLanguageImporter
		.rollback();
	assertEquals(1, deleted.size());
	assertEquals(4, deleted.get(0).getValue().intValue());
    }
    
    @Test
    public void shouldBeSkipShouldReturnCorrectValue(){
	GeonamesLanguageImporter geonamesLanguageImporter = new GeonamesLanguageImporter();
	LanguageDao languagesDao = EasyMock.createMock(LanguageDao.class);
	EasyMock.expect(languagesDao.count()).andReturn(10L);
	EasyMock.expect(languagesDao.count()).andReturn(0L);
	EasyMock.replay(languagesDao);
	
	geonamesLanguageImporter.setLanguageDao(languagesDao);
	
	Assert.assertTrue("country importer should be skiped if there is some country imported",geonamesLanguageImporter.shouldBeSkipped());
	Assert.assertFalse("country importer should be skiped if there is some country imported",geonamesLanguageImporter.shouldBeSkipped());
		
    }

}
