package service;

import exceptions.WrongActionTypeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuditService {

    private final String file = "src/main/resources/org/nacu/files/AUDIT";

    private AuditService() {}

    public static AuditService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private final static class SingletonHolder {
        private static final AuditService INSTANCE = new AuditService();
    }

    public void writeAction(ActionType actionType) {
        Path path = Paths.get(file);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        String name_action = getActionName(actionType);
        String str = name_action + "," + dateFormat.format(date) + "," + Thread.currentThread().getName() + "\n";

        try {
            Files.write(path, str.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getActionName(ActionType actionType) {
        switch (actionType) {
            case REGISTER: return "register";
            case LOGIN: return "login";
            case CREATE_OFFICE: return "create office";
            case DISPLAY_OFFICES: return "display offices";
            case MAKE_APPOINTMENT: return "make appointment";
            case DISPLAY_APPOINTMENTS: return "display appointments";
            case FINISH_APPOINTMENT: return "finish appointment";
            case RELEASE_PRESCRIPTION: return "release prescription";
            case DISPLAY_PRESCRIPTIONS: return "display prescriptions";
            case FINISH_PRESCRIPTION: return "finish prescription";
        }

        throw new WrongActionTypeException();
    }

    public enum ActionType {
        REGISTER, LOGIN, CREATE_OFFICE, DISPLAY_OFFICES, MAKE_APPOINTMENT, DISPLAY_APPOINTMENTS, FINISH_APPOINTMENT, RELEASE_PRESCRIPTION, DISPLAY_PRESCRIPTIONS, FINISH_PRESCRIPTION
    }
}
