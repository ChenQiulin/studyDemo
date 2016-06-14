package study.util;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;
import com.maxmind.geoip.timeZone;

import java.util.HashMap;
import java.util.Map;

public class CityLookup {
	private String countryCode;
	private String countryName;
	private String region;
	private String regionNameString;
	private String city;
	private String postalCode;
	private float latitude;
	private float longitude;
	private int metroCode;
	private int areaCode;
	private String timezone;
	private int dmaCode;

	private static LookupService cl;

	private static String GeoIPFilePath = "/www/appdata/geo-ip/GeoIP-133_20150623.dat";

	public CityLookup(String IP) throws Exception {

		if (cl == null) {
			cl = new LookupService(GeoIPFilePath, LookupService.GEOIP_STANDARD);
		}

		Location location = cl.getLocation(IP);

		if (location != null) {
			this.countryCode = location.countryCode;
			this.countryName = location.countryName;
			this.region = location.region;
			this.regionNameString = regionName.regionNameByCode(location.countryCode, location.region);
			this.city = location.city;
			this.postalCode = location.postalCode;
			this.latitude = location.latitude;
			this.longitude = location.longitude;
			this.metroCode = location.metro_code;
			this.areaCode = location.area_code;
			this.timezone = timeZone.timeZoneByCountryAndRegion(location.countryCode, location.region);
			this.dmaCode = location.dma_code;
		}
	}

	static Map<String, String> EuropeanUnionMembers = new HashMap<String, String>();
	static {
		EuropeanUnionMembers.put("AT", "Austria");
		EuropeanUnionMembers.put("BE", "Belgium");
		EuropeanUnionMembers.put("BG", "Bulgaria");
		EuropeanUnionMembers.put("HR", "Croatia");
		EuropeanUnionMembers.put("CY", "Cyprus");
		EuropeanUnionMembers.put("CZ", "Czech Republic");
		EuropeanUnionMembers.put("DK", "Denmark");
		EuropeanUnionMembers.put("EE", "Estonia");
		EuropeanUnionMembers.put("FI", "Finland");
		EuropeanUnionMembers.put("FR", "France");
		EuropeanUnionMembers.put("DE", "Germany");
		EuropeanUnionMembers.put("GR", "Greece");
		EuropeanUnionMembers.put("HU", "Hungary");
		EuropeanUnionMembers.put("IS", "Iceland");
		EuropeanUnionMembers.put("IE", "Ireland");
		EuropeanUnionMembers.put("IT", "Italy");
		EuropeanUnionMembers.put("LV", "Latvia");
		EuropeanUnionMembers.put("LT", "Lithuania");
		EuropeanUnionMembers.put("LU", "Luxembourg");
		EuropeanUnionMembers.put("MT", "Malta");
		EuropeanUnionMembers.put("NL", "Netherlands");
		EuropeanUnionMembers.put("PL", "Poland");
		EuropeanUnionMembers.put("PT", "Portugal");
		EuropeanUnionMembers.put("RO", "Romania");
		EuropeanUnionMembers.put("SK", "Slovakia");
		EuropeanUnionMembers.put("SI", "Slovenia");
		EuropeanUnionMembers.put("ES", "Spain");
		EuropeanUnionMembers.put("SE", "Sweden");
		EuropeanUnionMembers.put("GB", "United Kingdom");
	}

	public boolean isEUMember(String countryCod) {
		return EuropeanUnionMembers.containsKey(countryCod);
	}

	public int getDmaCode() {
		return dmaCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getRegion() {
		return region;
	}

	public String getRegionNameString() {
		return regionNameString;
	}

	public String getCity() {
		return city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public int getMetroCode() {
		return metroCode;
	}

	public int getAreaCode() {
		return areaCode;
	}

	public String getTimezone() {
		return timezone;
	}

	public static void main(String[] arg) {
		String[] ips = new String[] { "71.191.1.136", "36.69.17.151", "125.163.152.214", "180.241.195.116",
				"180.241.195.116", "180.241.195.116", "110.137.73.252", "108.12.108.148", "108.45.59.173",
				"71.191.1.136", "24.210.37.177", "68.189.72.73", "108.45.59.173", "71.191.1.136", "24.196.232.217",
				"72.229.62.224", "178.233.157.43", "71.191.1.136", "108.45.59.173", "67.143.226.209", "24.196.232.217",
				"70.162.29.104", "82.157.254.107", "197.37.147.165", "173.202.109.134", "14.161.5.130", "14.161.5.130",
				"67.168.53.121", "78.188.115.237", "76.73.187.14", "76.73.187.14", "74.57.159.249", "166.147.65.84",
				"188.124.240.147", "75.178.47.35", "47.16.67.237", "173.202.109.134", "70.162.29.104", "75.178.47.35"

		};

		System.out.println("start");
		for (String ip : ips) {
			try {
				// CityLookup lookup = new
				// CityLookup("/usr/local/share/GeoIP/GeoIPCity.dat","71.56.154.177");
				CityLookup lookup = new CityLookup(ip);

				System.out.println(lookup.getCountryName());
				System.out.print("ip=" + ip);
				System.out.print(" areaCode=" + lookup.getAreaCode());
				System.out.print(" City=" + lookup.getCity());
				System.out.print(" CountryCode=" + lookup.getCountryCode());
				System.out.print(" CountryName=" + lookup.getCountryName());
				System.out.print(" Latitude=" + lookup.getLatitude());
				System.out.print(" Longitude=" + lookup.getLongitude());
				System.out.print(" MetroCode=" + lookup.getMetroCode());
				System.out.print(" PostalCode=" + lookup.getPostalCode());
				System.out.print(" Region=" + lookup.getRegion());
				System.out.print(" RegionNameString=" + lookup.getRegionNameString());
				System.out.print(" Timezone=" + lookup.getTimezone());
				System.out.println("");
				System.out.println("");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
