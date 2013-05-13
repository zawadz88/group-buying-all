package pl.edu.pw.eiti.groupbuying.web.admin.service;

import pl.edu.pw.eiti.groupbuying.web.admin.model.PushNotification;

public interface PushService {
	void push(PushNotification pushNotification);
}
