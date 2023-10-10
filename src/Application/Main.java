package Application;

import java.io.*;
import java.util.*;

public class Main {

    private static final List<File> notes = new ArrayList();
    private final static String dirPath = "./notes";

    static {
        File f = new File(dirPath);
        File[] files = f.listFiles();
        assert files != null;
        notes.addAll(Arrays.asList(files));
    }

    public static void main(String[] args) {
        String choice = null;
        System.out.println();
        System.out.println("*/*/*/*Notes Application*/*/*/*");
        do {
            System.out.println();
            System.out.println("***Main menu***");
            System.out.println();
            System.out.println("Press 1 to write and save the note.");
            System.out.println("Press 2 to show all note topics.");
            System.out.println("Press 3 to edit a note with note topic.");
            System.out.println("Press 4 to read a note with note topic.");
            System.out.println("Press 5 to delete a note with note topic.");
            System.out.println("Press 6 to exit the Notes Application.");
            System.out.println();
            System.out.print("Choose the point of menu: ");
            System.out.println();

            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine();
            System.out.println();
            switch (choice) {
                case "1":
                    System.out.println("Type note topic.");
                    saveNote(creatingOrChoosingTopicNote());
                    break;
                case "2":
                    showAllTopics();
                    break;
                case "3":
                    System.out.println("What topic of note do you want to find for editing note?");
                    editNote(creatingOrChoosingTopicNote());
                    break;
                case "4":
                    System.out.println("What topic of note do you want to find for reading note?");
                    readNote(creatingOrChoosingTopicNote());
                    break;
                case "5":
                    System.out.println("What topic of note do you want to find for deleting note?");
                    deleteNote(creatingOrChoosingTopicNote());
                    break;
                case "6":
                    System.out.println("Application is finished.");
                    System.out.println("*/*/*/*Notes Application*/*/*/*");
                    break;
                default:
                    System.out.print("Didn't find the point of menu. Choose again.");
                    System.out.println();
            }
        } while (!choice.equals("6"));
    }

    private static String creatingOrChoosingTopicNote() {
        Scanner scanner = new Scanner(System.in);
        String topicNote = scanner.nextLine();
        return topicNote;
    }

    private static String creatingTextNote() {
        System.out.println("Type text note.");
        Scanner scanner = new Scanner(System.in);
        String textNote = scanner.nextLine();
        return textNote;
    }

    private static void saveNote(String topicNote) {
        int indexOf = getIndexOf(topicNote);
        if (indexOf != -1) {
            System.out.println();
            System.out.println("A note with the same topic has already been created. You need to create unique topic for each note! You will return to the main menu.");
            return;
        }

        File file = new File(dirPath + "/" + topicNote);
        FileOutputStream outputStream = null;

        try {
            byte[] buffer = creatingTextNote().getBytes();
            outputStream = new FileOutputStream(file);
            outputStream.write(buffer);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        notes.add(file);
        System.out.print("Your note was saved. You will return to the main menu.");
        System.out.println();
    }

    private static void showAllTopics() {
        System.out.println("Topic list: ");
        for (File x : notes)
            System.out.println(x.getName());
    }

    private static void editNote(String topicNote) {
        int indexOf = getIndexOf(topicNote);
        if (indexOf == -1) {
            System.out.println();
            System.out.println("The note was not found for note editing. You will return to the main menu.");
            return;
        }

        File myFile = notes.get(indexOf);
        myFile.delete();
        notes.remove(indexOf);

        File file = new File(dirPath + "/" + topicNote);

        try {
            FileOutputStream outputStream = null;
            byte[] buffer = creatingTextNote().getBytes();
            outputStream = new FileOutputStream(file);
            outputStream.write(buffer);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        notes.add(file);
        System.out.println();
        System.out.print("Your note was edited and saved. You will return to the main menu.");
        System.out.println();
    }

    private static void readNote(String topicNote) {

        int indexOf = getIndexOf(topicNote);
        if (indexOf == -1) {
            System.out.println();
            System.out.println("The topic of note was not found for note reading. You will return to the main menu.");
            return;
        }

        File foundedFile = notes.get(indexOf);
        System.out.println();
        System.out.println("Topic note: " + topicNote);
        Date dateOfLastModified = new Date(foundedFile.lastModified());
        System.out.println("Note date: " + dateOfLastModified);

        try {
            FileInputStream inputStream = new FileInputStream(foundedFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                System.out.print("Note: " + new String(buffer, 0, bytesRead));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void deleteNote(String topicNote) {
        int indexOf = getIndexOf(topicNote);
        if (indexOf == -1) {
            System.out.println();
            System.out.println("The note was not found for note deleting. You will return to the main menu.");
            return;
        }
        File myFile = notes.get(indexOf);
        myFile.delete();
        notes.remove(indexOf);
        System.out.println();
        System.out.println("The note was deleted. You will return to the main menu.");
    }

    private static int getIndexOf(String topicNote) {
        return notes.indexOf(new File(dirPath + "/" + topicNote));
    }
}
