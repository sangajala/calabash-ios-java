/**
 * 
 */
package calabash.java;

import static calabash.java.Utils.getIntFromHash;
import static calabash.java.Utils.getStringFromHash;

import org.jruby.RubyArray;
import org.jruby.RubyHash;

/**
 * Represents an UI element.
 * 
 */
public final class UIElement {

	private final RubyHash data;
	private final String query;
	private final CalabashWrapper calabashWrapper;

	public UIElement(RubyHash data, String query,
			CalabashWrapper calabashWrapper) {
		this.data = data;
		this.query = query;
		this.calabashWrapper = calabashWrapper;
	}

	/**
	 * Get element's class
	 * 
	 * @return
	 */
	public String getElementClass() {
		return getStringFromHash(data, "class");
	}

	/**
	 * Gets the element id
	 * 
	 * @return
	 */
	public String getId() {
		return getStringFromHash(data, "id");
	}

	/**
	 * Gets the label
	 * 
	 * @return
	 */
	public String getLabel() {
		return getStringFromHash(data, "label");
	}

	/**
	 * Get description about this element
	 * 
	 * @return
	 */
	public String getDescription() {
		return getStringFromHash(data, "description");
	}

	/**
	 * Gets the rectangle
	 * 
	 * @return
	 */
	public Rect getRect() {
		RubyHash rect;
		try {
			rect = (RubyHash) data.get("rect");
			if (rect == null)
				return null;
		} catch (Exception e) {
			return null;
		}

		return new Rect(getIntFromHash(rect, "x"), getIntFromHash(rect, "y"),
				getIntFromHash(rect, "width"), getIntFromHash(rect, "height"),
				getIntFromHash(rect, "center_x"), getIntFromHash(rect,
						"center_y"));
	}

	/**
	 * Get the rectangle representing frame
	 * 
	 * @return
	 */
	public Rect getFrame() {
		RubyHash rect;
		try {
			rect = (RubyHash) data.get("frame");
		} catch (Exception e) {
			return null;
		}

		return new Rect(getIntFromHash(rect, "x"), getIntFromHash(rect, "y"),
				getIntFromHash(rect, "width"), getIntFromHash(rect, "height"),
				null, null);
	}

	/**
	 * Touches this element
	 * 
	 * @throws CalabashException
	 */
	public void touch() throws CalabashException {
		calabashWrapper.touch(query);
	}

	/**
	 * Flashes this UIElement
	 * 
	 * @throws CalabashException
	 */
	public void flash() throws CalabashException {
		calabashWrapper.flash(query);
	}

	/**
	 * Gets the text from this UI element if it supports text
	 * 
	 * @return
	 * @throws CalabashException
	 */
	public String getText() throws CalabashException {
		RubyArray result = calabashWrapper.query(query, "text");
		if (result.size() > 0) {
			return result.get(0).toString();
		}

		return null;
	}

	/**
	 * Gets the property from this UIElement. This method can be used to fetch
	 * any properties which are present in the UIElement
	 * 
	 * @param properties
	 *            list of required properties
	 * @return
	 * @throws CalabashException
	 */
	public Object[] getProperyValues(String... properties)
			throws CalabashException {
		RubyArray values = calabashWrapper.query(query, properties);
		return Utils.toJavaArray(values);
	}

	/**
	 * Scrolls the element to the direction
	 * 
	 * @param direction
	 *            Direction to scroll
	 * @throws CalabashException
	 */
	public void scroll(ScrollDirection direction) throws CalabashException {
		calabashWrapper.scroll(query, direction);
	}

	/**
	 * Scroll to the cell according to the specified options
	 * 
	 * @param options
	 * @throws CalabashException
	 */
	public void scrollToCell(ScrollOptions options) throws CalabashException {
		if (options == null)
			throw new CalabashException("options should be present");

		calabashWrapper.scrollToCell(query, options);
	}

	/**
	 * Scrolls through each cells and calling the callback for each cell This
	 * method works only for tableviews
	 * 
	 * @param callback
	 *            callback to be invoked
	 * @throws CalabashException
	 */
	public void scrollThroughEachCell(CellIterator callback)
			throws CalabashException {
		scrollThroughEachCell(null, callback);
	}

	/**
	 * Scrolls through each cells and calling the callback for each cell This
	 * method works only for tableviews
	 * 
	 * @param options
	 *            options to control scrolling
	 * @param callback
	 *            call to be invoked
	 * @throws CalabashException
	 */
	public void scrollThroughEachCell(ScrollOptions options,
			CellIterator callback) throws CalabashException {
		if (callback == null)
			throw new CalabashException("callback should be present");

		calabashWrapper.scrollThroughEachCell(query, options, callback);
	}

	public String toString() {
		return String.format(
				"class: %s, label: %s, description: %s, rect: %s, frame: %s",
				getElementClass(), getLabel(), getDescription(), getRect(),
				getFrame());
	}

}
