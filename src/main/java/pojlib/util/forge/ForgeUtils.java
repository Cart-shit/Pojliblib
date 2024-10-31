package pojlib.util.forge;

import static pojlib.util.Constants.FORGE_INSTALLER_URL;

import android.content.Context;
import android.content.Intent;

import java.io.File;


public class ForgeUtils {

    public static String getInstallerUrl(String version) {
        return String.format(FORGE_INSTALLER_URL, version);
    }

    public static void addAutoInstallArgs(Intent intent, File modInstallerJar, boolean createProfile, Context ctx) {
        intent.putExtra("javaArgs", "-javaagent:"+ ctx.getFilesDir().getParent() +"/forge_installer/forge_installer.jar"
                + (createProfile ? "=NPS" : "") + // No Profile Suppression
                " -jar "+modInstallerJar.getAbsolutePath());
    }
    public static void addAutoInstallArgs(Intent intent, File modInstallerJar, String modpackFixupId, Context ctx) {
        intent.putExtra("javaArgs", "-javaagent:"+ ctx.getFilesDir().getParent() +"/forge_installer/forge_installer.jar"
                + "=\"" + modpackFixupId +"\"" +
                " -jar "+modInstallerJar.getAbsolutePath());
    }
}
