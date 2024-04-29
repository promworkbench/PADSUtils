package org.processmining.padsutils.utils;

import org.processmining.acceptingpetrinet.models.AcceptingPetriNet;
import org.processmining.models.connections.GraphLayoutConnection;
import org.processmining.plugins.pnml.base.FullPnmlElementFactory;
import org.processmining.plugins.pnml.base.Pnml;
import org.processmining.plugins.pnml.base.PnmlElementFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class InOutUtils {

    public static ByteArrayOutputStream exportAcceptingPetriNetToOutputStream(AcceptingPetriNet apn) throws IOException {
        GraphLayoutConnection layout = new GraphLayoutConnection(apn.getNet());
        PnmlElementFactory factory = new FullPnmlElementFactory();
        Pnml pnml = new Pnml();
        synchronized (factory) {
            Pnml.setFactory(factory);
            pnml = (new Pnml()).convertFromNet(apn.getNet(), apn.getInitialMarking(), apn.getFinalMarkings(), layout);
            pnml.setType(Pnml.PnmlType.PNML);
        }

        String text = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" + pnml.exportElement(pnml);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(text.getBytes());
        return os;
    }

    public static void addContentToZip(ZipOutputStream out, byte[] content, String fileName) throws IOException {
        ZipEntry e = new ZipEntry(fileName.substring(0, fileName.lastIndexOf(".")) + fileName.substring(fileName.lastIndexOf(".")));
        out.putNextEntry(e);
        out.write(content);
        out.closeEntry();
    }
}
