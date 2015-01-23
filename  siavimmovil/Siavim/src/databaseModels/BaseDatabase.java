package databaseModels;

public class BaseDatabase {
	public static final String DatabaseName = "SIAVIMDatabase";
	public static final int VersionDatabase = 24;
	public static String cedulaUser = "";
	
	public static void setCedulaUser(String cedulaUser) {
		BaseDatabase.cedulaUser = cedulaUser;
	}
	public static String getCedulaUser() {
		return cedulaUser;
	}
	
}
