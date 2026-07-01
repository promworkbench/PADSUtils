package org.processmining.padsutils.inout;

import org.processmining.models.episode_leemans.EpisodeModel;
import org.processmining.models.episode_leemans.Episode;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class EpExport {
	
	public static void exportEpisodes(EpisodeModel em, File file) {
		try {
			BufferedWriter writer = null;
			writer = new BufferedWriter(new FileWriter(file));
			List<Episode> episodes = new ArrayList<>();
			episodes = em.getEpisodes();
			for (int i = 0; i < episodes.size(); i++) {
			    Episode ep = episodes.get(i);
			    String epStr = ep.toString();
			    writer.write(epStr);
			    writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
