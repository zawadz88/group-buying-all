package pl.edu.pw.eiti.groupbuying.rest.service;

import pl.edu.pw.eiti.groupbuying.rest.model.PushNotification;

public interface PushService {
	void push(PushNotification pushNotification);
}
