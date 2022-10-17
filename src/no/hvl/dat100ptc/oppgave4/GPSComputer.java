package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
			//distance = distance + GPSUtils.distance(gpspoints[i], gpspoints[i + 1]); samme som
			
		}
		
		return distance;

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
					
			double elevationDiff = (gpspoints[i + 1].getElevation() - gpspoints[i].getElevation());
			
			if (elevationDiff > 0) {
				elevation += elevationDiff;
		
			}
		}
		
		return elevation;

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {
		
		int totalTime = gpspoints[gpspoints.length - 1].getTime() - gpspoints[0].getTime();
		
		return totalTime;
	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		
		double[] speeds = new double [gpspoints.length - 1];
		
		for (int i = 0; i < gpspoints.length - 1; i++) {
			
			speeds[i] = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
			
		}
		
		return speeds;

	}
	
	public double maxSpeed() {
		
		double maxspeed = GPSUtils.findMax(speeds());
		
		return maxspeed;
		
	}

	public double averageSpeed() {

		double average = totalDistance() / totalTime() * 3.6;
		
		return average;
		
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double speedmph = speed * MS;
		
		if (speedmph < 10) {
			met = 4.0;
		} 
		else if (speedmph < 12) {
			met = 6.0;
		}
		else if (speedmph < 14) {
			met = 8.0;
		}
		else if (speedmph < 16) {
			met = 10.0;
		}
		else if (speedmph < 20) {
			met = 12.0;
		}
		else {
			met = 16.0;
		}
			
		kcal = met * weight * secs / 3600;
		
		return kcal;
		//kcals per sec
	}
	
	private static double WEIGHT = 80.0;
	
	public double totalKcal(double weight) {

		double totalkcal = kcal(weight, totalTime(), averageSpeed()) * totalTime();
		
		return totalkcal / WEIGHT;
		
	}
	
	//private static double WEIGHT = 80.0; flyttet den opp så den gav mening.
	
	public void displayStatistics() {
		
		int time = totalTime();
		double distance = totalDistance() / 1000;    // /1000 for å gjøre om til km
		double elevation = totalElevation();
		double maxSpeed = maxSpeed();
		double averageSpeed = averageSpeed();
		double energy = totalKcal(WEIGHT);
		
		System.out.println("==============================================");
		System.out.println("Total Time     :" + GPSUtils.formatTime(time));
		System.out.println("Total distance :" + distance + " km");
		System.out.println("Total elevation:" + elevation + " m");
		System.out.println("Max speed      :" + maxSpeed + " km/t");
		System.out.println("Average speed  :" + averageSpeed + " km/t");
		System.out.println("Energy         :" + totalKcal(WEIGHT) + " kcal");
		System.out.println("==============================================");
	}

}
