package src.ui;

public class Menu {
    public String DisplayMenu(String title, String[] options){
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        for (int k = 0; k < options.length; k++){
            sb.append("\n" + k+1 + ". " + options[k]);
        }
        return sb.toString();
    }

}
