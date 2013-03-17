package pl.edu.pw.eiti.groupbuying.android.api;

public enum OfferState {
	WAITING, ACTIVE, FINISHED;

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
