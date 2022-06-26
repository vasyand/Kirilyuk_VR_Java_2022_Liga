package application.menu;

import java.util.Set;

public class Filter {
    private final String flag;
    private final Set<String> validValues;

    public Filter(String flag, Set<String> validValues) {
        this.flag = flag;
        this.validValues = validValues;
    }

    public String getFlag() {
        return flag;
    }

    public Set<String> getValidValues() {
        return validValues;
    }
}
