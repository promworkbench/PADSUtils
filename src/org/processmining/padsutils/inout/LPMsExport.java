package org.processmining.padsutils.inout;

import org.processmining.acceptingpetrinet.models.AcceptingPetriNet;
import org.processmining.lpm.util.LocalProcessModelRanking;
import org.processmining.padsutils.utils.InOutUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipOutputStream;

public class LPMsExport {

    public static void exportLocalProcessModelRanking(LocalProcessModelRanking lpmr,
                                                      File file) throws IOException {
        String fileName = file.getName();
        String prefix = fileName.substring(0, fileName.indexOf("."));

        ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(file.toPath()));

        for (int i = 0; i < lpmr.getSize(); ++i) {
            // convert lpm to accepting petri net
            AcceptingPetriNet apn = lpmr.getNet(i).getAcceptingPetriNet();

            String zfName = prefix + "_" + i + ".pnml";

            // add the net file to the zip folder
            ByteArrayOutputStream oos = InOutUtils.exportAcceptingPetriNetToOutputStream(apn);
            InOutUtils.addContentToZip(out, oos.toByteArray(), zfName);

        }
        out.close();
    }
}
