import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    private static StringBuilder log;
    private static SimpleDateFormat dt;

    public static void main(String[] args) {
        dt = new SimpleDateFormat("dd.mm.yyyy HH:mm:ss");
        File gamesDir = new File("C:/Users/Мишка/Desktop/Games");
        log = new StringBuilder();

        if (gamesDir.exists()) {
            if (gamesDir.isDirectory()) {
                log.append(dt.format(new Date()) + " - директория " + gamesDir.getName() + " найдена по адресу " + gamesDir.getAbsolutePath() + "\n");
                if (gamesDir.canRead()) {
                    log.append(dt.format(new Date()) + " - директория " + gamesDir.getName() + " доступна для чтения" + "\n");
                    if (gamesDir.canWrite()) {
                        log.append(dt.format(new Date()) + " - директория " + gamesDir.getName() + " доступна для записи" + "\n");
                        //src, res, savegames, temp
                        File src = createDir(gamesDir, "src");
                        if (src != null && src.isDirectory() == true) {
                            //main, test
                            File main = createDir(src, "main");
                            //Main.java, Utils.java
                            if (main != null && main.isDirectory() == true) {
                                File mainJava = createFile(main, "Main.java");
                                File utilsJava = createFile(main, "Utils.java");
                            }

                            File test = createDir(src, "test");
                        }
                        File res = createDir(gamesDir, "res"); //создаем папку res
                        if (res != null && res.isDirectory() == true) {
                            //drawables, vectors, icons
                            File drawables = createDir(res, "drawables");
                            File vectors = createDir(res, "vectors");
                            File icons = createDir(res, "icons");
                        }
                        File saveGames = createDir(gamesDir, "savegames"); //создаем папку savegames
                        File temp = createDir(gamesDir, "temp"); //создаем папку temp
                        if (temp != null && temp.isDirectory() == true) {
                            //temp.txt
                            if (!createLogFile(temp, "temp.txt")) {
                                System.out.println(log);
                            }
                        }
                    } else {
                        log.append(dt.format(new Date()) + " - " + gamesDir.getName() + " недоступна для записи" + "\n");
                    }

                } else {
                    log.append(dt.format(new Date()) + " - " + gamesDir.getName() + " недоступна для чтения" + "\n");
                }

            } else {
                log.append(dt.format(new Date()) + " - " + gamesDir.getName() + " не является директорией" + "\n");
            }
        } else {
            log.append(dt.format(new Date()) + " - директория Games не найдена" + "\n");
        }
    }

    public static File createDir(File dir, String name) {
        File newDir = new File(dir.getAbsolutePath() + "/" + name);
        if (newDir.exists()) {
            log.append(dt.format(new Date()) + " - субдиректория " + newDir.getName() + " уже существует в директории " + newDir.getName() + "\n");

            return newDir;
        }

        if (newDir.mkdir()) {
            log.append(dt.format(new Date()) + " - в директорию " + dir.getName() + " добавлена субдиректория " + newDir.getName() + "\n");
            return newDir;
        }
        log.append(dt.format(new Date()) + " - субдиректория " + name + "  не создана в директории " + dir.getName() + "\n");
        return null;
    }

    public static File createFile(File dir, String name) {
        File file = new File(dir.getAbsolutePath() + "/" + name);
        if (file.exists()) {
            log.append(dt.format(new Date()) + " - файл " + file.getName() + " уже существует в директории " + dir.getName() + "\n");
            return file;
        }

        try {
            //создаем сам файл
            if (file.createNewFile()) {
                log.append(dt.format(new Date()) + " - файл " + name + " (" + file.length() + " байт) успешно создан" + " в директории " + dir.getName() + "\n");
                return file;
            } else {
                log.append(dt.format(new Date()) + " - файл " + name + " не создан" + "\n");
            }

        } catch (IOException ex) {
            log.append(dt.format(new Date()) + " ошибка при создании файла " + name + ": " + ex.getMessage() + "\n");
        }
        return null;
    }

    public static boolean createLogFile(File dir, String name) {
        try (FileOutputStream fos = new FileOutputStream(dir.getAbsolutePath() + "/" + name)) {
            byte[] bytes = log.toString().getBytes();
            //запись байтов в файл
            fos.write(bytes, 0, bytes.length);
            log.append(dt.format(new Date()) + " - лог обновлён " + "\n");
            return true;
        } catch (IOException ex) {
            log.append(dt.format(new Date()) + "- ошибка записи лога " + ex.getMessage() + "\n");
        }
        return false;
    }
}