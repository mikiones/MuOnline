package eu.derbed.openmu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mikiones
 */
public class ServersRunner {

	private static final Logger log = LoggerFactory.getLogger(ServersRunner.class);

	// we need to deterene the working directory to .jmusrv at home directory

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(final String[] args) throws Exception {
		if (true) {
			log.debug("disabled for now");
			return;
		}
		final ServersRunner s = new ServersRunner();
		s.PreareSettings();

		final OldGameServer gs = new OldGameServer();
		final FS fs = new FS();
//	 gs.start();
		fs.start();
	}

	public void PreareSettings()
	{
		final File jmuserv = new File(System.getProperty("user.home")+"/.jmuserv/");
		if (jmuserv.exists())
		{
			System.out.println("Found .jmuserv home diretory contiunue loading settings ... ");

		} else {
			createDirestories();
			copyConfData();
			copyDataFiles();
			System.out.println("Now Edit conf Files in " + System.getProperty("user.home")+"/jmuserv/etc and run server again!!!");
			System.exit(0);
		}
	}

	/**
	 * Create default directories structure in home directory for server usage
	 */
	public void createDirestories() {
		System.out.println("Create Server home directories structure...");
		final File HomeDir = new File(System.getProperty("user.home")
				+ "/.jmuserv/");
		final File etcDir = new File(HomeDir, "/etc/");
		final File dataDir = new File(HomeDir, "/data/");
		final File mapsDir = new File(dataDir, "/maps/");
		final File logDir = new File(HomeDir, "/logs/");
		final File scripts = new File(HomeDir, "/scripts/");
		if (HomeDir.mkdir()) {
			System.out.println(".jmuserv... OK");
		}
		if (etcDir.mkdir()) {
			System.out.println(".jmuserv/etc... OK");
		}
		if (dataDir.mkdir()) {
			System.out.println(".jmuserv/data... OK");
		}
		if (mapsDir.mkdir()) {
			System.out.println(".jmuserv/data/maps... OK");
		}
		if (logDir.mkdir()) {
			System.out.println(".jmuserv/logs... OK");
		}
		if (scripts.mkdir()) {
			System.out.println(".jmuserv/scripts... OK");
		}
	}

	public static void copyFile(final File in, final File out)
			throws IOException
	{
		final FileChannel inChannel = new
				FileInputStream(in).getChannel();
		final FileChannel outChannel = new
				FileOutputStream(out).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(),
					outChannel);
		}
		catch (final IOException e) {
			throw e;
		}
		finally {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		}
	}


	public void copyConfData() {
		final String [] names = {
				"database",
				"GameServer",
				"MuLog",
				"ConnectServer"
		};

		final File targetDir= new File(System.getProperty("user.home")+"/.jmuserv/etc/");

		for(final String name:names)
		{
			final File target= new File(targetDir,name+".ini");
			final File from = new File("templates/"+name+".templ");
			try {
				copyFile(from, target);
			} catch (final IOException e) {
				System.out.println("copy: "+name+ ".ini to "+target.getPath() +"  ....Error !!!");
				e.printStackTrace();
			}
			System.out.println("copy: "+name+ ".ini to "+target.getPath() +"  ....OK");
		}
	}
	public void coppyTerainsData()
	{
		System.out.println("Copy Terrains data ... ");
		final File dest = new File (System.getProperty("user.home")+"/.jmuserv/data/maps/");
		dest.mkdirs();
		final File from_dir= new File ("./data/maps/");
		final String[] ls = from_dir.list();
		for (final String tempName: ls){
			final File to_coppy= new File(from_dir,tempName);
			try {
				copyFile(to_coppy, new File(dest,tempName));
			} catch (final IOException e) {
				System.out.println("copy: "+tempName+ " to "+dest.getPath() +"  ....Error !!!");
				e.printStackTrace();
			}
			System.out.println("copy: "+tempName+ " to "+dest.getPath() +"  ....OK");
		}

	}

	public void copyDataFiles()
	{
		System.out.println("Copy Data files ..... ");
		final File dest = new File(System.getProperty("user.home")+"/.jmuserv/data");
		final String[] filesToCoppy = {"./data/item.txt"};
		for(final String file:filesToCoppy)
		{
			final File to_copy= new File(file);
			final File dest_copy = new File(dest,to_copy.getName());
			try {
				copyFile(to_copy, dest_copy );
				System.out.println("copy: "+dest_copy.getName()+ " to "+dest_copy.getPath() +"  ....OK");
			} catch (final IOException e) {
				System.out.println("copy: "+dest_copy.getName()+ " to "+dest_copy.getPath() +"  ....ERROR!!!");
				e.printStackTrace();
			}

		}
		coppyTerainsData();
	}
}