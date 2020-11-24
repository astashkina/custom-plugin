import com.intellij.ide.BrowserUtil;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import database.H2jdbcCreateDemo;
import database.H2jdbcInsertDemo;
import database.H2jdbcRecordDemo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;

public class TweetAction extends AnAction {

    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);

        // здесь наброски более красивого решения, но надо доработать (тут как раз через PSI)
        // String selectedText = editor.getSelectionModel().getSelectedText(); -> получить выделенный текст
        // String selectedText = editor.getDocument().getText(); -> получить весь текст
        // String selectedText = psiFile.getText();

        //FileViewProvider fileViewProvider = psiFile.getViewProvider();
        // PsiElement psiElement = fileViewProvider.findElementAt(1);

//        for (int i = 0; i < 400; i++) {
//            System.out.println(psiFile.findElementAt(i).getText());
//        }
//
//        PsiElement psiElement = psiFile.findElementAt(1);
//        String selectedText = psiElement.getText();
//
//        if (selectedText != null) {
//            String encoded = "";
//            try {
//                encoded = URLEncoder.encode(selectedText, StandardCharsets.UTF_8.toString());
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//
//            String url = String.format("https://yandex.ru/search/?lr=10716&text=%s", encoded);
//            BrowserUtil.browse(url);
//        } else {
//            Messages.showMessageDialog("Selection is empty, could you please select something?", "Tweet Action", Messages.getInformationIcon());
//        }

        String allText = editor.getDocument().getText();
        List<String> allLines = Arrays.asList(allText.split("\n"));

        // тут расписано как считывать из файла и парсить, умеет вычленять цифры (количество уязвимостей)
//        StringBuilder allVulnerabilities = new StringBuilder();
//        try {
//            Scanner scanner = new Scanner(new File("./src/main/java/testFile.txt"));
//            while (scanner.hasNextLine()) {
//                allVulnerabilities.append(scanner.nextLine());
//                allVulnerabilities.append("\n");
//            }
//            scanner.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        String str  = allVulnerabilities.toString();
//        List<String> allVulns = Arrays.asList(str.split("\n"));
//
//        for (int i = 0; i < allVulns.size(); i++) {
//            List<String> oneLine = Arrays.asList(allVulns.get(i).split(" "));
//            System.out.println(oneLine.get(1));
//        }

        // ищем импорты

        List<String> finalImports = new ArrayList<>();
        H2jdbcCreateDemo.createDemo();
        H2jdbcInsertDemo.insertDemo();

        for (int i = 0; i < allLines.size(); i++) {
            List<String> oneLine = Arrays.asList(allLines.get(i).split(" "));
            if (oneLine.get(0).equals("import")) {
                finalImports.add(removeCharAt(oneLine.get(1), oneLine.get(1).length() - 1));
            }
        }

        HashMap<String, Integer> map = H2jdbcRecordDemo.recordDemo(finalImports);

        for (int i = 0; i < finalImports.size(); i++) {
            System.out.println("#" + i + ". " + finalImports.get(i));
        }
        System.out.println("--------------------------");


        System.out.println(map);
        for (String key: map.keySet()){

            String k = key;
            String value = map.get(key).toString();
            Messages.showMessageDialog("Imported library " + k + " has " + value + " vulnerability(-ies)",
                    "Vulnerability(-ies) in " + k + " library", Messages.getInformationIcon());
        }


    }

    @Override
    public boolean isDumbAware() {
        return false;
    }
}
