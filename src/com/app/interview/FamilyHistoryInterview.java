package com.app.interview; /**
 * Created by: arif hosain
 * Mail: arif@innoweb.co
 * Created at : 4/29/2024
 */

import com.app.App;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

class TreeNode {
    String question;
    TreeNode yesNode;
    TreeNode noNode;

    TreeNode(String question) {
        this.question = question;
    }
}

public class FamilyHistoryInterview {
    private TreeNode root;
    private StringBuilder interviewData;

    public FamilyHistoryInterview() {
        // Build the binary tree for the app
        buildTree();
        interviewData = new StringBuilder();
    }

    private void buildTree() {
        // Constructing the binary tree
        root = new TreeNode("Is there anyone in your family who has been diagnosed with any major disorders or health conditions?");
        root.yesNode = new TreeNode("Who in your family has been diagnosed with a major disorder or health condition?");
        root.noNode = new TreeNode("Does anyone in your family have a history of hereditary disorders?");
        root.yesNode.yesNode = new TreeNode("Please specify who and what condition they have been diagnosed with.");
        root.yesNode.noNode = new TreeNode("Are they currently receiving treatment for their condition?");
        root.yesNode.noNode.yesNode = new TreeNode("Have they received treatment for their condition in the past?");
        root.yesNode.noNode.noNode = new TreeNode("Is there any other relevant family medical history information that you think is important to mention?");
        root.noNode.yesNode = new TreeNode("Please specify the hereditary disorder and any known family history.");
        root.noNode.noNode = new TreeNode("Has anyone in your family been diagnosed with any specific type of disorder or disease?");
        root.noNode.noNode.yesNode = new TreeNode("Please specify the disorder and the family member(s) affected.");
        root.noNode.noNode.noNode = new TreeNode("Are there any known health risk factors that affect your family members?");
        root.noNode.noNode.noNode.yesNode = new TreeNode("Please specify the risk factors and any family members affected.");
        root.noNode.noNode.noNode.noNode = new TreeNode("Is there any other relevant family medical history information that you think is important to mention?");
    }

    public void startInterview() {
        TreeNode currentNode = root;
        interviewData.append("Patient ID: ").append(App.SELECTED_PATIENT_ID).append(" - Patient Name: ").append(App.SELECTED_PATIENT_NAME).append("\n");
        while (currentNode != null) {
            String answer = JOptionPane.showInputDialog(null, currentNode.question, "Family History Interview", JOptionPane.QUESTION_MESSAGE);
            if (answer == null) {
                // User cancelled the app
                JOptionPane.showMessageDialog(null, "Interview cancelled.");
                return;
            }
            answer = answer.toLowerCase();
            interviewData.append(currentNode.question).append(": ").append(answer).append("\n");
            if (answer.equals("yes")) {
                currentNode = currentNode.yesNode;
            } else if (answer.equals("no")) {
                currentNode = currentNode.noNode;
            } else {
                JOptionPane.showMessageDialog(null, "Please answer with 'yes' or 'no'.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
        // End of app
        JOptionPane.showMessageDialog(null, "Interview completed. Thank you!");

        // Save interview data to a text file
        saveInterviewDataToFile();
    }

    private void saveInterviewDataToFile() {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));
        String filePath = "D:/interview_data_" + currentTime + ".txt"; // Specify the file path under the C drive with the current time
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(interviewData.toString());
            JOptionPane.showMessageDialog(null, "Interview data saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving interview data.");
        }
    }
}
