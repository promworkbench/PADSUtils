package org.processmining.padsutils.utils;

import org.deckfour.xes.info.impl.XLogInfoImpl;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.models.graphbased.directed.petrinet.elements.Transition;
import org.processmining.plugins.petrinet.replayresult.PNRepResult;
import org.processmining.plugins.petrinet.replayresult.StepTypes;
import org.processmining.plugins.replayer.replayresult.SyncReplayResult;

import java.util.Iterator;
import java.util.List;

public class ProcessMiningObjectPrinter {

  public static void printAlignments(XLog xlog, PNRepResult pnReplayRes) {

    for (SyncReplayResult align : pnReplayRes) {

      XTrace traceRepresentative = xlog.get(align.getTraceIndex().first());
      
      StringBuilder logRowBuilder = new StringBuilder();
      StringBuilder modelRowBuilder = new StringBuilder();
      logRowBuilder.append("|");
      modelRowBuilder.append("|");

      int curIndexInTrace = 0;
      XEvent event;
      Transition t;
      List<Object> nodeInstances = align.getNodeInstance();
      Iterator<Object> nodeIt = nodeInstances.iterator();
      for (StepTypes step : align.getStepTypes()) {
        Object nodeCur = nodeIt.next();
        switch (step) {
          case LMGOOD:
            event = traceRepresentative.get(curIndexInTrace);
            t = (Transition) nodeCur;
            // Sync event info
            logRowBuilder.append(String.format("%10s|", XLogInfoImpl.NAME_CLASSIFIER.getClassIdentity(event)));
            modelRowBuilder.append(String.format("%10s|", t.getLabel()));
            curIndexInTrace++;
            break;
          case L:
            event = traceRepresentative.get(curIndexInTrace);
            // Log move info
            logRowBuilder.append(String.format("%10s|", XLogInfoImpl.NAME_CLASSIFIER.getClassIdentity(event)));
            modelRowBuilder.append(String.format("%10s|", ">>"));
            curIndexInTrace++;
            break;
          case LMNOGOOD:
          case LMREPLACED:
          case LMSWAPPED:
          case MINVI:
          case MREAL:
            t = (Transition) nodeCur;
            // Log move info
            logRowBuilder.append(String.format("%10s|", ">>"));
            modelRowBuilder.append(String.format("%10s|", t.getLabel()).toString());
            break;
          default:
            break;
        }
      }
      String logRow = logRowBuilder.toString();
      String modelRow = modelRowBuilder.toString();
      String separatorRow = "|" + "-".repeat(logRow.length() - 2) + "|";
      
      String alignment = logRow + "\n" + separatorRow + "\n" + modelRow;
      
      System.out.println(alignment);

    }

  }
}
