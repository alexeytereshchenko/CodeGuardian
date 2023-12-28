package io.github.alexeytereshchenko.guardian.extention;

public class GuardianCheckStyleExtension {
  private boolean enable = true;
  private String fileUrl;
  private int errorThreshold = 0;
  private boolean showViolations = true;
  private String version = "10.12.6";

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public int getErrorThreshold() {
    return errorThreshold;
  }

  public void setErrorThreshold(int errorThreshold) {
    this.errorThreshold = errorThreshold;
  }

  public boolean isShowViolations() {
    return showViolations;
  }

  public void setShowViolations(boolean showViolations) {
    this.showViolations = showViolations;
  }
}
