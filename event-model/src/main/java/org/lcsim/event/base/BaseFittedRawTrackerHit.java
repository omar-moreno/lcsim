
package org.lcsim.event.base;

import java.util.List;

import org.lcsim.detector.IDetectorElement;
import org.lcsim.detector.identifier.IIdentifierHelper;
import org.lcsim.detector.identifier.Identifier;
import org.lcsim.event.FittedRawTrackerHit;
import org.lcsim.event.SimTrackerHit;

/**
 * A basic implementation of RawTrackerHit.
 * 
 * @author Tony Johnson
 * @author Jeremy McCormick <jeremym@slac.stanford.edu>
 * @version $Id: BaseFittedRawTrackerHit.java,v 1.13 2012/03/14 00:48:43 jeremy Exp $
 */
public class BaseFittedRawTrackerHit extends BaseHit implements FittedRawTrackerHit {
    
    protected int time;
    protected short cellId;
    protected short[] adcValues;
    protected List<SimTrackerHit> simTrackerHits;
    
    public BaseFittedRawTrackerHit(
    		short id,
    		int time,
    		short[] adcValues) {
    	this.cellId = id;
    	this.packedID = new Identifier(id);
    	this.time = time;
    	this.adcValues = adcValues;
    }

    public BaseFittedRawTrackerHit(
            int time, 
            short cellId, 
            short[] adcValues, 
            List<SimTrackerHit> simTrackerHits, 
            IDetectorElement detectorElement)
    {
        this.time = time;
        this.cellId = cellId;
        this.adcValues = adcValues;
        this.simTrackerHits = simTrackerHits;
        //this.packedID = new Identifier(cellId);
        this.detectorElement = detectorElement;
    }
    
    /**
     * Constructor for use by subclasses.
     */
    protected BaseFittedRawTrackerHit()
    {}

    public int getTime()
    {
        return time;
    }

    public short getCellID()
    {
        return cellId;
    }

    public short[] getADCValues()
    {
        return adcValues;
    }

    public List<SimTrackerHit> getSimTrackerHits()
    {
        return simTrackerHits;
    }   
    
    /**
     * Use SimTrackerHits to find the IdentifierHelper.
     */
    public IIdentifierHelper getIdentifierHelper()
    {

        IIdentifierHelper helper = null;
        if (detectorElement != null) 
        {
            helper = detectorElement.getIdentifierHelper();
        }
        else if (simTrackerHits != null && simTrackerHits.size() > 0)
        {
            helper = simTrackerHits.get(0).getIdentifierHelper();
        }
        else
        {
            throw new RuntimeException("Could not retrieve IdentifierHelper for RawTrackerHit.  No SimTrackerHits found.");
        }
        return helper;
    }
}
