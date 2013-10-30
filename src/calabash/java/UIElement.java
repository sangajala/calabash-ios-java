/**
 * 
 */
package calabash.java;

import static calabash.java.Utils.getIntFromHash;
import static calabash.java.Utils.getStringFromHash;

import org.jruby.RubyArray;
import org.jruby.RubyHash;

import calabash.java.SwipeOptions.Force;

/**
 * Represents an UI element.
 * 
 */
public class UIElement implements IAction {

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
	 * Checks if this element exists
	 * 
	 * @return true if it exists, false otherwise
	 * @throws CalabashException
	 */
	public boolean exists() throws CalabashException {
		return calabashWrapper.elementExists(query);
	}

	public void touch() throws CalabashException {
		calabashWrapper.touch(query);
	}

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
	 * Get additional properties for this element. This helps you to get
	 * specific property value by performing selectors on the query result
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * UIElement element = iosApplication.query(&quot;tableView&quot;).first();
	 * Object value = element.getPropertyValue(&quot;numberOfSections&quot;);
	 * </pre>
	 * <p>
	 * This method doesn't support advanced selectors like the ones that are
	 * supported by calabash Ruby. Support for advanced selectors will be added
	 * in the later versions.
	 * 
	 * @param selector
	 *            Selector to apply to the query.
	 * @return Value for the property after applying the selector. Type of this
	 *         value depends on the selector.
	 * @throws CalabashException
	 */
	public Object getPropertyValue(String selector) throws CalabashException {
		RubyArray values = calabashWrapper.query(query, selector);
		if (values != null && !values.isEmpty()) {
			Object object = values.get(0);
			return Utils.toJavaObject(object);
		}

		return null;
	}

	public void scroll(Direction direction) throws CalabashException {
		calabashWrapper.scroll(query, direction);
	}

	public void swipe(Direction direction) throws CalabashException {
		calabashWrapper.swipe(query, direction, null);
	}

	public void swipe(Direction direction, Force force)
			throws CalabashException {
		calabashWrapper.swipe(query, direction, new SwipeOptions(force, null));
	}

	public void swipe(Direction direction, SwipeOptions options)
			throws CalabashException {
		calabashWrapper.swipe(query, direction, options);
	}

	public void pinchIn() throws CalabashException {
		calabashWrapper.pinch(query, "in");
	}

	public void pinchOut() throws CalabashException {
		calabashWrapper.pinch(query, "out");
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

	/**
	 * Gets all the child elements for this element
	 * 
	 * @return List of UIElement
	 * @throws CalabashException
	 */
	public UIElements children() throws CalabashException {
		String q = query + " child *";
		RubyArray result = calabashWrapper.query(q);
		return new UIElements(result, q, calabashWrapper);
	}

	/**
	 * Inspects the current element and it's child elements and call callback
	 * for each element
	 * 
	 * @param callback
	 *            Callback to be invoked
	 * @throws CalabashException
	 */
	public void inspect(InspectCallback callback) throws CalabashException {
		Utils.inspectElement(this, 0, callback);
	}

	public String toString() {
		return String.format(
				"class: %s, label: %s, description: %s, rect: %s, frame: %s",
				getElementClass(), getLabel(), getDescription(), getRect(),
				getFrame());
	}

	/**
	 * Gets the underlying query used to locate this element
	 * 
	 * @return Query
	 */
	public String getQuery() {
		return this.query;
	}

	public boolean equals(Object obj) {
		if (obj instanceof UIElement) {
			UIElement that = (UIElement) obj;
			boolean equal = false;
			if (this.getFrame() != null && that.getFrame() != null)
				equal = this.getFrame().equals(that.getFrame());

			if (equal && this.getRect() != null && that.getRect() != null)
				equal = this.getRect().equals(that.getRect());

			if (equal && this.getId() != null && that.getId() != null)
				equal = this.getId().equals(that.getId());

			if (equal && this.getLabel() != null && that.getLabel() != null)
				equal = this.getLabel().equals(that.getLabel());

			if (equal && this.getElementClass() != null
					&& that.getElementClass() != null)
				equal = this.getElementClass().equals(that.getElementClass());

			return equal;
		}

		return super.equals(obj);
	}

}
