package io.github.alexeytereshchenko.guardian.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public abstract class DownloadCheckstyleFile extends DefaultTask {

  private final HttpClient httpClient;
  private final Logger log;

  public DownloadCheckstyleFile() {
    this.httpClient = HttpClient.newHttpClient();
    this.log = LoggerFactory.getLogger(this.getName());
  }

  @Input
  @Optional
  public abstract Property<String> getUrl();

  @Input
  @Optional
  public abstract Property<String> getDestinationPath();

  @TaskAction
  public void downloadCheckstyleFile() throws URISyntaxException, IOException, InterruptedException {
    String destinationPath = getDestinationPath().get();
    String url = getUrl().get();

    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI(url))
          .GET()
          .build();

      HttpResponse<InputStream> response = httpClient.send(request, BodyHandlers.ofInputStream());

      int statusCode = response.statusCode();
      if (statusCode > 299) {
        throw new InvalidUserDataException();
      }

      File file = new File(destinationPath);
      file.getParentFile().mkdirs();
      file.createNewFile();

      try (FileOutputStream fos = new FileOutputStream(file);
           InputStream body = response.body()) {
        fos.write(body.readAllBytes());
      }

    } catch (Exception e) {
      String message = "Cannot download a checkstyle file by url: %s in %s".formatted(url, destinationPath);
      log.error(message, e);
      throw e;
    }
  }
}
