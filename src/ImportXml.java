
import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

public class ImportXml extends DefaultHandler {

	private Recipe r = null;
	private Malt m = null; 
	private Hop h = null;

	private String currentList = null; //current List name
	private String currentElement = null; // current element name

	public static void main(String argv[]) {
		if (argv.length != 1) {
			System.err.println("Usage: cmd filename");
			System.exit(1);
		}

		// Use an instance of ourselves as the SAX event handler
		DefaultHandler handler = new ImportXml();
		// Use the default (non-validating) parser
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// Set up output stream
			out = new OutputStreamWriter(System.out, "UTF8");

			// Parse the input
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new File(argv[0]), handler);

		} catch (SAXParseException spe) {
			// Error generated by the parser
			System.out.println("\n** Parsing error" + ", line "
					+ spe.getLineNumber() + ", uri " + spe.getSystemId());
			System.out.println("   " + spe.getMessage());

			// Use the contained exception, if any
			Exception x = spe;
			if (spe.getException() != null)
				x = spe.getException();
			x.printStackTrace();

		} catch (SAXException sxe) {
			// Error generated by this application
			// (or a parser-initialization error)
			Exception x = sxe;
			if (sxe.getException() != null)
				x = sxe.getException();
			x.printStackTrace();

		} catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			pce.printStackTrace();

		} catch (IOException ioe) {
			// I/O error
			ioe.printStackTrace();
		}

		System.exit(0);
	}

	static private Writer out;

	private String indentString = "    "; // Amount to indent

	private int indentLevel = 0;

	//===========================================================
	// SAX DocumentHandler methods
	//===========================================================

	public void setDocumentLocator(Locator l) {
		// Save this to resolve relative URIs or to give diagnostics.
		try {
			out.write("LOCATOR");
			out.write("\n SYS ID: " + l.getSystemId());
			out.flush();
		} catch (IOException e) {
			// Ignore errors
		}
	}

	public void startDocument() throws SAXException {
	}

	public void endDocument() throws SAXException {
		emit(r.toXML());
		r.testRecipe();

		try {
			nl();
			out.flush();
		} catch (IOException e) {
			throw new SAXException("I/O error", e);
		}
	}

	public void startElement(String namespaceURI, String lName, // local name
			String qName, // qualified name
			Attributes attrs) throws SAXException {
		String eName = lName; // element name
		if ("".equals(eName))
			eName = qName; // namespaceAware = false

		if (eName.equalsIgnoreCase("DETAILS")) {
			currentList = "DETAILS";
			r = new Recipe();
			emit("New Recipe created");
		} else if (eName.equalsIgnoreCase("FERMENTABLES")) {
			currentList = "FERMENTABLES";
		} else if (eName.equalsIgnoreCase("HOPS")) {
			currentList = "HOPS";
		} 
		else if (eName.equalsIgnoreCase("MASH") && currentList == null) {
			currentList = "MASH";
		} 
		else if (eName.equalsIgnoreCase("MISC")) {
			currentList = "MISC";
		} else if (eName.equalsIgnoreCase("ITEM")) { // this is an item in a list
			if (currentList.equals("FERMENTABLES")) {
				m = new Malt();
			} else if (currentList.equals("HOPS")) {
				h = new Hop();
			} else if (currentList.equals("MISC")) {
				// TODO: new misc object
			} 

		}

		else {
			currentElement = eName;
		}
	}

	public void endElement(String namespaceURI, String sName, // simple name
			String qName // qualified name
	) throws SAXException {

		if (qName.equalsIgnoreCase("ITEM")
				&& currentList.equalsIgnoreCase("FERMENTABLES")) {
			r.addMalt(m);
			m = null;
		} else if (qName.equalsIgnoreCase("ITEM")
				&& currentList.equalsIgnoreCase("HOPS")) {
			r.addHop(h);
			h = null;
		} else if (qName.equalsIgnoreCase("FERMENTABLS") ||
				   qName.equalsIgnoreCase("HOPS") || 
				   qName.equalsIgnoreCase("DETAILS")) {
			currentList = null;
		}
	}

	public void characters(char buf[], int offset, int len) throws SAXException {
		String s = new String(buf, offset, len);

		if (!s.trim().equals("")) {

			if (currentList.equals("FERMENTABLES")) {
				if (currentElement.equalsIgnoreCase("MALT")) {
					m.setName(s.trim());
				} else if (currentElement.equalsIgnoreCase("AMOUNT")) {
					m.setAmount(Double.parseDouble(s));
				} else if (currentElement.equalsIgnoreCase("POINTS")) {
					m.setPppg(Double.parseDouble(s));
				} else if (currentElement.equalsIgnoreCase("COSTLB")) {
					m.setCost( s.trim() );
				} else if (currentElement.equalsIgnoreCase("UNITS")) {
					m.setUnits(s.trim());
				} else if (currentElement.equalsIgnoreCase("LOV")) {
					m.setLov(Double.parseDouble(s));
				} else if (currentElement.equalsIgnoreCase("DescrLookup")) {
					m.setDesc(s.trim());
				}

			}

			else if (currentList.equalsIgnoreCase("HOPS")) {
				if (currentElement.equalsIgnoreCase("HOP")) {
					h.setName(s.trim());
				} else if (currentElement.equalsIgnoreCase("AMOUNT")) {
					h.setAmount(Double.parseDouble(s));
				} else if (currentElement.equalsIgnoreCase("ALPHA")) {
					h.setAlpha(Double.parseDouble(s));
				} else if (currentElement.equalsIgnoreCase("UNITS")) {
					h.setUnits(s.trim());
				} else if (currentElement.equalsIgnoreCase("FORM")) {
					h.setForm(s.trim());
				} else if (currentElement.equalsIgnoreCase("COSTOZ")) {
					// h.setCost( Double.parseDouble(s) );
				} else if (currentElement.equalsIgnoreCase("ADD")) {
					h.setAdd(s.trim());
				} else if (currentElement.equalsIgnoreCase("DescrLookup")) {
					h.setDesc(s.trim());
				}
			}

			else if (currentList.equalsIgnoreCase("DETAILS")) {
				if (currentElement.equalsIgnoreCase("NAME")) {
					r.setName(s.trim());
				} else if (currentElement.equalsIgnoreCase("EFFICIENCY")) {
					r.setEfficiency(Double.parseDouble(s));
				} else if (currentElement.equalsIgnoreCase("ATTENUATION")) {
					r.setAttenuation(Double.parseDouble(s));
				} else if (currentElement.equalsIgnoreCase("PRESIZE")) {
					r.setPreBoil(Double.parseDouble(s));
				} else if (currentElement.equalsIgnoreCase("SIZE")) {
					r.setPostBoil(Double.parseDouble(s));
				} else if (currentElement.equalsIgnoreCase("SIZE_UNITS")) {
					r.setVolUnits(s.trim());
				}

			}
		}
	}

	public void ignorableWhitespace(char buf[], int offset, int len)
			throws SAXException {
		nl();
		emit("IGNORABLE");
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		nl();
		emit("PROCESS: ");
		emit("<?" + target + " " + data + "?>");
	}

	//===========================================================
	// SAX ErrorHandler methods
	//===========================================================

	// treat validation errors as fatal
	public void error(SAXParseException e) throws SAXParseException {
		throw e;
	}

	// dump warnings too
	public void warning(SAXParseException err) throws SAXParseException {
		System.out.println("** Warning" + ", line " + err.getLineNumber()
				+ ", uri " + err.getSystemId());
		System.out.println("   " + err.getMessage());
	}

	//===========================================================
	// Utility Methods ...
	//===========================================================

	// Wrap I/O exceptions in SAX exceptions, to
	// suit handler signature requirements
	private void emit(String s) throws SAXException {
		try {
			out.write(s);
			out.flush();
		} catch (IOException e) {
			throw new SAXException("I/O error", e);
		}
	}

	// Start a new line
	// and indent the next line appropriately
	private void nl() throws SAXException {
		String lineEnd = System.getProperty("line.separator");
		try {
			out.write(lineEnd);
			for (int i = 0; i < indentLevel; i++)
				out.write(indentString);
		} catch (IOException e) {
			throw new SAXException("I/O error", e);
		}
	}
}