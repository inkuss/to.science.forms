/*Copyright (c) 2016 "hbz"

This file is part of zettel.

etikett is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package models;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.typesafe.config.ConfigFactory;

import play.data.validation.Constraints.Required;
import services.ZettelHelper;

/**
 * @author Jan Schnasse
 *
 */
@SuppressWarnings("javadoc")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResearchData {
	/**
	 * The id under which this model is registered in the ZettelRegister
	 */
	public final static String id = "katalog:data";

	private static final String ID = "@id";

	private static final String LABEL = "prefLabel";

	@Required(message = "Please provide a title")
	private String title;

	private List<String> author;

	private String yearOfCopyright;

	private String license;

	private String abstractText;

	private String professionalGroup;

	private String embargo;

	List<String> ddc;

	String language;

	String medium;

	String dataOrigin;

	private List<String> subject;
	private List<String> doi;
	private Map<String, Object> context;

	private Object documentId = "frl:1";

	/**
	 * Create an empty ResearchData model
	 */
	public ResearchData() {
		this.title = new String();
		this.author = new ArrayList<>();
		this.yearOfCopyright = new String();
		this.license = new String();
		this.abstractText = new String();
		this.professionalGroup = new String();
		this.embargo = new String();
		this.ddc = new ArrayList<>();
		this.language = new String();
		this.medium = new String();
		this.dataOrigin = new String();
		this.subject = new ArrayList<>();
		this.doi = new ArrayList<>();
		initContext();
	}

	private void initContext() {
		try (InputStream in =
				new URL(ConfigFactory.load().getString("contextUrl")).openStream()) {

			context = new ObjectMapper().readValue(
					CharStreams.toString(new InputStreamReader(in, "UTF-8")),
					HashMap.class);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Initialize all values at once
	 */
	public ResearchData(String title, List<String> author, String yearOfCopyright,
			String license, String abstractText, String professionalGroup,
			String embargo, List<String> ddc, String language, String medium,
			String dataOrigin, List<String> subject, List<String> doi) {
		super();
		this.title = title;
		this.author = author;
		this.yearOfCopyright = yearOfCopyright;
		this.license = license;
		this.abstractText = abstractText;
		this.professionalGroup = professionalGroup;
		this.embargo = embargo;
		this.ddc = ddc;
		this.language = language;
		this.medium = medium;
		this.dataOrigin = dataOrigin;
		this.subject = subject;
		this.doi = doi;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAuthor() {
		return author;
	}

	public void setAuthor(List<String> author) {
		this.author = author;
	}

	public String getYearOfCopyright() {
		return yearOfCopyright;
	}

	public void setYearOfCopyright(String yearOfCopyright) {
		this.yearOfCopyright = yearOfCopyright;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public List<String> getDoi() {
		return doi;
	}

	public void setDoi(List<String> doi) {
		this.doi = doi;
	}

	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public String getProfessionalGroup() {
		return professionalGroup;
	}

	public void setProfessionalGroup(String professionalGroup) {
		this.professionalGroup = professionalGroup;
	}

	public String getEmbargo() {
		return embargo;
	}

	public void setEmbargo(String embargo) {
		this.embargo = embargo;
	}

	public List<String> getDdc() {
		return ddc;
	}

	public void setDdc(List<String> ddc) {
		this.ddc = ddc;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<String> getSubject() {
		return subject;
	}

	public void setSubject(List<String> subject) {
		this.subject = subject;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getDataOrigin() {
		return dataOrigin;
	}

	public void setDataOrigin(String dataOrigin) {
		this.dataOrigin = dataOrigin;
	}

	@Override
	public String toString() {
		return ZettelHelper.objectToString(getJsonLdMap());
	}

	public Map<String, Object> getJsonLdMap() {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put(ID, documentId);
		jsonMap.put("primaryTopic", documentId);
		jsonMap.put("title", getTitle());
		if (author != null && !author.isEmpty())
			jsonMap.put("creator", author);
		jsonMap.put("@context", context.get("@context"));
		return jsonMap;

	}
}
