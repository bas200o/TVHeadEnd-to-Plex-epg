package parsers;

import CONFIG.CONFIG;
import Models.Channel;
import Models.Event;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.ArrayList;

public class TVXMLParser {

    public static void xmlParser(ArrayList<Channel> channels) {
        Document root = DocumentHelper.createDocument();

        Element document = root.addElement("tv");

        for (Channel c : channels) {

            Element channel = document.addElement("channel");
            channel.addAttribute("id", "" + c.getChannelNumber());

            channel.addElement("display-name")
                    .addText(c.getChannelName());

            channel.addElement("icon")
                    .addAttribute("src", c.getChannelImg());
        }

        for (Channel c : channels) {

            for (Event e : c.getEvents()) {

                Element program = document.addElement("programme");
                program.addAttribute("start", e.getStartTime() + " +0200");
                program.addAttribute("stop", e.getEndTime() + " +0200");
                program.addAttribute("channel", c.getChannelNumber() + "");

                program.addElement("title")
                        .addAttribute("lang", "dut")
                        .addText(e.getTitle());

                program.addElement("desc")
                        .addAttribute("lang", "dut")
                        .addText(e.getDescription());
            }

        }

        try {
            OutputStream outputStream = new FileOutputStream(CONFIG.outputPath);

            String header = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                    "<!DOCTYPE tv SYSTEM \"xmltv.dtd\">";

            outputStream.write(header.getBytes());

            OutputFormat format = OutputFormat.createPrettyPrint();

            XMLWriter writer = new XMLWriter(outputStream, format);

            writer.write(document);

            outputStream.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
