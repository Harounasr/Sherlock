package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Backing bean for the help.xhtml facelet.
 *
 * @author Lennart Hohls
 */
@Named
@RequestScoped
public class HelpBean {

    /**
     * The logger for this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * The message to display as help.
     */
    private String helpMessage;

    /**
     * Constructor for HelpBean.
     *
     * @param logger     The logger instance (Injected).
     * @param appSession The active session (Injected).
     */
    @Inject
    public HelpBean(SerializableLogger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
        setMessage();
    }

    /**
     * Retrieves the help message.
     *
     * @return The help message.
     */
    public String getHelpMessage() {
        return helpMessage;
    }

    /**
     * Sets help message.
     *
     * @param helpMessage the help message
     */
    public void setHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
    }

    private void setMessage() {
        helpMessage =
                "Welcome to the Software Sherlock Help Page!\n\n" +

                "1. Anonymous Users (System Role)\n" +
                "   - General Information:\n" +
                "     Anonymous users can view the Impressum and Privacy Policy on the homepage.\n" +
                "   - Registration:\n" +
                "     To register, click on the 'Register' link and fill in the required fields. Ensure that the chosen username is unique, and the"
                + " email follows the specified format.\n"
                +
                "   - Login:\n" +
                "     Use valid credentials to log in. After five failed attempts, the account gets locked for security purposes.\n" +
                "   - Password Reset:\n" +
                "     Click on 'Forgot password' to initiate a password reset via a confirmation email. Follow the instructions in the email to "
                + "complete the process.\n\n"
                +

                "2. Logged-In Users (System Role)\n" +
                "   - Course List:\n" +
                "     Upon login, users see their course overview. If not enrolled, all available courses are displayed.\n" +
                "   - Course Enrollment:\n" +
                "     Users can join a course by clicking 'Join' on the course list. Ensure that you have the correct course code.\n" +
                "   - Profile Editing:\n" +
                "     Edit your profile by clicking on the profile icon in the top right corner. Update personal information and change your "
                + "password if needed.\n\n"
                +

                "3. Course Participants (Course Role)\n" +
                "   - Task List:\n" +
                "     Participants view available tasks on the course page. Click on a task to see detailed information.\n" +
                "   - Submission:\n" +
                "     Upload solutions in a ZIP file on the task page. Multiple submissions are allowed until the deadline.\n" +
                "   - Submission History:\n" +
                "     View submission history on the task page. Track your progress and feedback.\n" +
                "   - Test Results:\n" +
                "     Check test results for the latest submission. Understand your performance and areas for improvement.\n\n" +

                "4. Tutors (Course Role)\n" +
                "   - Submission List:\n" +
                "     Tutors can view submissions assigned to them for review. Provide constructive feedback and evaluate submissions.\n" +
                "   - Testat Creation:\n" +
                "     Create testats with detailed feedback and grades for submissions. Ensure fairness and consistency in assessments.\n\n" +

                "5. Lecturers\n" +
                "   - Course and Task Management:\n" +
                "     Create, delete, and edit courses and tasks. Organize course materials and information.\n" +
                "   - Participant Management:\n" +
                "     Manage participants, appoint tutors, and change user roles. Ensure a well-organized and collaborative learning environment.\n" +
                "   - Testat and Submission Review:\n" +
                "     Review testats and submissions. Provide guidance and support to participants.\n\n" +

                "6. Administrators\n" +
                "   - User Management:\n" +
                "     View, edit, and delete user accounts, change roles, and reset passwords. Maintain system integrity and security.\n" +
                "   - Application Management:\n" +
                "     Modify application settings, including name, logo, colors, and email requirements. Customize the application to suit "
                + "institutional branding.\n";


    }
}
