/**
 * 
 */
package calabash.java;

import java.io.File;
import java.net.URI;

/**
 * Configure calabash with various configuration options
 * 
 */
public final class CalabashConfiguration {

	private File screenshotsDirectory;
	private String device;
	private String appBundlePath;
	private ScreenshotListener listener;
	private URI deviceEndPoint;
	private File logsDirectory;
	private boolean noLaunch = false;
	private File playbackDir;
	private String os;
	private String bundleId;
	private String sdkVersion;
	private long pauseTimeInMs = -1;
	private String deviceTarget;
	private boolean detectConnectedDevice;
	private boolean debug;

	/**
	 * Gets the screenshots directory. If not set, this returns the current
	 * working directory
	 * 
	 * @return
	 */
	public File getScreenshotsDirectory() {
		if (screenshotsDirectory == null)
			return new File(System.getProperty("user.dir"));
		return screenshotsDirectory;
	}

	/**
	 * Sets the screenshot directory. Screenshots will be written to this
	 * directory
	 * 
	 * @param screenshotsDirectory
	 *            Directory
	 * @throws CalabashException
	 */
	public void setScreenshotsDirectory(File screenshotsDirectory)
			throws CalabashException {
		if (!screenshotsDirectory.isDirectory())
			throw new CalabashException(screenshotsDirectory.getAbsolutePath()
					+ " is invalid");

		if (!screenshotsDirectory.canWrite())
			throw new CalabashException(screenshotsDirectory.getAbsolutePath()
					+ " is not writable");

		this.screenshotsDirectory = screenshotsDirectory;
	}

	/**
	 * Gets the device if set
	 * 
	 * @return
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * Sets the device which has to be used when running tests
	 * 
	 * @param device
	 *            Valid values are ios, ipad, iphone
	 */
	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * controls which device to launch on. When set Calabash uses instruments to
	 * launch. The default is 'simulator' unless a device is plugged in via USB,
	 * in which case it uses that device
	 * 
	 * @param target
	 *            target to set to
	 */
	public void setDeviceTarget(String target) {
		this.deviceTarget = target;
	}

	/**
	 * Gets the device target
	 * 
	 * @return
	 */
	public String getDeviceTarget() {
		return this.deviceTarget;
	}

	/**
	 * Gets the APP bundle path
	 * 
	 * @return path if set, null otherwise
	 */
	public String getAppBundlePath() {
		return appBundlePath;
	}

	/**
	 * Sets the APP bundle path. This is required only when calabash fails to
	 * auto detect the app bundle path.
	 * 
	 * @param appBundlePath
	 *            Path to the bundle
	 */
	public void setAppBundlePath(String appBundlePath) {
		this.appBundlePath = appBundlePath;
	}

	/**
	 * Sets the screenshot listener. Listener will be invoked whenever calabash
	 * takes a screenshot.
	 * 
	 * @param listener
	 *            ScreenshotListener instance
	 */
	public void setScreenshotListener(ScreenshotListener listener) {
		this.listener = listener;
	}

	/**
	 * Gets the current screenshot listener
	 * 
	 * @return ScreenshotListener if set, null otherwise
	 */
	public ScreenshotListener getScreenshotListener() {
		return listener;
	}

	/**
	 * Gets the device endpoint.
	 * 
	 * @return URI if set, null otherwise
	 */
	public URI getDeviceEndPoint() {
		return deviceEndPoint;
	}

	/**
	 * Sets the device IP address. This is required only when the device that
	 * needs to run the test is not in localhost. By default calabash uses
	 * http://localhost:37265
	 * 
	 * @param deviceEndPoint
	 *            A valid device endpoint
	 */
	public void setDeviceEndPoint(URI deviceEndPoint) {
		this.deviceEndPoint = deviceEndPoint;
	}

	/**
	 * Gets the logs directory
	 * 
	 * @return
	 */
	public File getLogsDirectory() {
		return logsDirectory;
	}

	/**
	 * Sets the logs directory. Setting null will disable logging
	 * 
	 * @param logsDirectory
	 *            Logs directory to set
	 * @throws CalabashException
	 *             If the directory is invalid or not writable
	 */
	public void setLogsDirectory(File logsDirectory) throws CalabashException {
		if (logsDirectory == null) {
			this.logsDirectory = null;
			return;
		}

		if (!logsDirectory.isDirectory())
			throw new CalabashException(logsDirectory.getAbsolutePath()
					+ " is not a directory");

		if (!logsDirectory.canWrite())
			throw new CalabashException(logsDirectory.getAbsolutePath()
					+ " is not writable");

		this.logsDirectory = logsDirectory;
	}

	/**
	 * Gets the boolean value indicating whether logging is enabled
	 * 
	 * @return true if logging is enabled, false otherwise
	 */
	public boolean isLoggingEnabled() {
		return getLogsDirectory() != null;
	}

	/**
	 * Determines test run should launch the application
	 * 
	 * @return true if it should launch, false otherwise
	 */
	public boolean getNoLaunch() {
		return noLaunch;
	}

	/**
	 * Setting this will stop launching the application every time test starts
	 * 
	 * @param noLaunch
	 *            true will disable launching
	 */
	public void setNoLaunch(boolean noLaunch) {
		this.noLaunch = noLaunch;
	}

	/**
	 * Sets the directory where playback files should be read from
	 * 
	 * @param dir
	 *            Directory where playback files are present
	 * @throws CalabashException
	 */
	public void setPlaybackDirectory(File dir) throws CalabashException {
		if (!dir.isDirectory())
			throw new CalabashException("Invalid recording directory");

		if (!dir.canWrite())
			throw new CalabashException("Recording directory is not writable");

		this.playbackDir = dir;
	}

	/**
	 * Gets the playback directory
	 * 
	 * @return
	 */
	public File getPlaybackDirectory() {
		return playbackDir;
	}

	/**
	 * Sets the OS used by the device/simulator
	 * 
	 * @param os
	 */
	public void setOS(String os) {
		this.os = os;
	}

	/**
	 * Gets the OS used by the device/simulator
	 * 
	 * @return
	 */
	public String getOS() {
		return this.os;
	}

	/**
	 * Sets the application bundle id
	 * 
	 * @param bundleId
	 */
	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	/**
	 * Gets the bundle id
	 * 
	 * @return
	 */
	public String getBundleId() {
		return this.bundleId;
	}

	/**
	 * Sets the iOS SDK version
	 * 
	 * @param sdkVersion
	 * @return
	 */
	public void setSDKVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	/**
	 * Gets the SDK version
	 * 
	 * @return
	 */
	public String getSDKVersion() {
		return this.sdkVersion;
	}

	/**
	 * Sets the pause time in milliseconds.
	 * 
	 * Calabash will pause for the specified seconds after performing every
	 * actions
	 * 
	 * @param pauseTimeInMs
	 *            Milliseconds to wait after each actions
	 */
	public void setPauseTime(long pauseTimeInMs) {
		this.pauseTimeInMs = pauseTimeInMs;
	}

	/**
	 * Gets the pause time in milliseconds
	 * 
	 * @return Pause time in milliseconds
	 */
	public long getPauseTime() {
		return pauseTimeInMs;
	}

	/**
	 * Setting this to true will enable calabash to search for devices connected
	 * via USB. If no devices are connected, setting this will hang the calabash
	 * launch
	 * 
	 * @param value
	 */
	public void setDetectConnectedDevice(boolean value) {
		this.detectConnectedDevice = value;
	}

	/**
	 * Gets a value indicating whether calabash will search for devices
	 * connected via USB.
	 * 
	 * @return
	 */
	public boolean getDetectConnectedDevice() {
		return this.detectConnectedDevice;
	}

	/**
	 * Gets a value indicating whether debug level output is enabled
	 * 
	 * @return true if enabled, false otherwise
	 */
	public boolean getDebug() {
		return debug;
	}

	/**
	 * Setting debug will output debug information from calabash to console
	 * 
	 * @param value
	 */
	public void setDebug(boolean value) {
		this.debug = value;
	}
}
