package pl.edu.pw.eiti.groupbuying.core.dto;

/**
 * State of the offer
 * @author Piotr Zawadzki
 *
 */
public enum OfferState {
	WAITING, ACTIVE, FINISHED;

	/**
	 * Returns an {@link OfferState} object based on an integer value
	 * @param value integer
	 * @return {@link OfferState} object
	 * @throws IllegalArgumentException throws this if {@code value} was not in range
	 */
	public static OfferState getState(final int value) {
		OfferState state = null;
		switch (value) {
		case 0:
			state = WAITING;
			break;
		case 1:
			state = ACTIVE;
			break;
		case 2:
			state = FINISHED;
			break;
		default:
			throw new IllegalArgumentException("value: '" + value
					+ "' not permitted for Offer.State!");
		}
		return state;
	}

}
