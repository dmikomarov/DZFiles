package Data;

public enum Locale {
  EN("en"),
  RU("ru");
  private final String desc;
  Locale (String desc) {
    this.desc = desc;
  }
  public String getDesc() {
    return desc;
  }
}
