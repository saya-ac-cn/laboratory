package ac.cn.saya.laboratory.tools;


import ac.cn.saya.laboratory.entity.UserMemory;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;
import java.util.Base64;
import java.util.Base64.Decoder;
/**
 * @Title: UploadUtils
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/11/11 21:30
 * @Description: 上传单元
 */

public class UploadUtils {

    /**
     * 上传logo -> class/files
     *
     * @param image
     * @param request
     * @return
     * @throws Exception
     */
    public static Result<String> uploadLogo(String image, HttpServletRequest request) throws Exception {
        try {
            String header = "data:image";
            String[] imageArr = image.split(",");
            //是img
            if (imageArr[0].contains(header)) {
                // 去掉头部
                image = imageArr[1];
                // 写入磁盘标志
                String success = "fail";
                //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
                UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
                //将字符串格式的image转为二进制流（biye[])的decodedBytes
                Decoder decoder = Base64.getDecoder();
                byte[] decodedBytes = decoder.decode(image);
                /********生成今天的日期**********/
                String datetime = DateUtils.getCurrentDateTime(DateUtils.fileFormat);
                //url路径 files/picture/logo/用户名/yyyyMMdd
                String urlPath = File.separator + "warehouse" + File.separator + "picture" + File.separator + "logo" + File.separator + userSession.getUser() + File.separator + datetime;
                //上传文件路径-/picture/目录下该用户当天的文件夹
                String path = System.getProperty("user.home", "/home/saya") + urlPath;
                File filepath = new File(path);
                //判断路径是否存在，如果不存在就创建一个
                //这里不能判断父目录getParentFile()是否存在
                if (!filepath.exists()) {
                    filepath.mkdirs();
                }
                //保存的文件名
                String imgName = RandomUtil.getRandomFileName() + ".png";
                //存放到服务器上的真实路径(不是url路径)
                String truePath = path + File.separator + imgName;
                //新建一个文件输出器，并为它指定输出位置imgFilePath
                FileOutputStream out = new FileOutputStream(truePath);
                //利用文件输出器将二进制格式decodedBytes输出
                out.write(decodedBytes);
                //关闭文件输出器
                out.close();
                //上传成功
                return ResultUtil.success(urlPath + File.separator + imgName);
            } else {
                return ResultUtil.error(-2, "请选择有效的图片");
            }
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("上传图片异常", e, UploadUtils.class);
            return ResultUtil.error(-1, "上传失败");
        }

    }

    /**
     * 上传图片（笔记、动态图片）
     * @param image
     * @param imgeUrl
     * @param request
     * @return
     * @throws Exception
     */
    public static Result<String> uploadPicture(String image, String imgeUrl, HttpServletRequest request) throws Exception {
        try {
            String header = "data:image";
            String[] imageArr = image.split(",");
            //是img
            if (imageArr[0].contains(header)) {
                // 去掉头部
                image = imageArr[1];
                // 写入磁盘标志
                String success = "fail";
                //将字符串格式的image转为二进制流（biye[])的decodedBytes
                Decoder decoder = Base64.getDecoder();
                byte[] decodedBytes = decoder.decode(image);
                /********生成今天的日期**********/
                String datetime = DateUtils.getCurrentDateTime(DateUtils.fileFormat);
                //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
                UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
                //url路径 files/picture/{wallpaper,news}/用户名/yyyyMMdd
                String urlPath = File.separator + "warehouse" + File.separator + "picture" + File.separator + imgeUrl + File.separator + userSession.getUser() + File.separator + datetime;
                //上传文件路径-/picture/目录下该用户当天的文件夹
                String path = System.getProperty("user.home", "/home/saya") + urlPath;
                File filepath = new File(path);
                //判断路径是否存在，如果不存在就创建一个
                //这里不能判断父目录getParentFile()是否存在
                if (!filepath.exists()) {
                    filepath.mkdirs();
                }
                //保存的文件名
                String imgName = RandomUtil.getRandomFileName() + ".png";
                //存放到服务器上的真实路径(不是url路径)
                String truePath = path + File.separator + imgName;
                //新建一个文件输出器，并为它指定输出位置imgFilePath
                FileOutputStream out = new FileOutputStream(truePath);
                //利用文件输出器将二进制格式decodedBytes输出
                out.write(decodedBytes);
                //关闭文件输出器
                out.close();
                //上传成功
                return ResultUtil.success(urlPath + File.separator + imgName);
            } else {
                return ResultUtil.error(-2, "请选择有效的图片");
            }
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("上传图片异常", e, UploadUtils.class);
            return ResultUtil.error(-1, "上传失败");
        }
    }

    /**
     * 上传文件
     *
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    public static Result<String> uploadFile(MultipartFile file, HttpServletRequest request) throws Exception {
        try {
            if (file == null) {
                return ResultUtil.error(-3, "文件不能为空");
            }
            //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
            UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
            //文件类型
            String fileType = null;
            // 原文件名称
            String fileName = file.getOriginalFilename();
            // 判断文件类型
            fileType = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (fileType == null) {
                return ResultUtil.error(-3, "文件类型不能为空");
            } else {
                if (!"BAT".equals(fileType.toUpperCase())
                        || !"EXE".equals(fileType.toUpperCase())) {
                    // 写入磁盘标志
                    String success = "fail";
                    /********生成今天的日期**********/
                    String datetime = DateUtils.getCurrentDateTime(DateUtils.fileFormat);
                    //url路径 files/picture/document/用户名/yyyyMMdd
                    String urlPath = File.separator + "warehouse" + File.separator + "document" + File.separator + "file" + File.separator + userSession.getUser() + File.separator + datetime;
                    //上传文件路径-/picture/目录下该用户当天的文件夹
                    String path = System.getProperty("user.home", "/home/saya") + urlPath;
                    File filepath = new File(path);
                    //判断路径是否存在，如果不存在就创建一个
                    //这里不能判断父目录getParentFile()是否存在
                    if (!filepath.exists()) {
                        filepath.mkdirs();
                    }
                    //保存的文件名
                    String newFileName = RandomUtil.getRandomFileName() + '.' + fileName;
                    File saveFile = new File(filepath.getAbsolutePath() + File.separator + newFileName);
                    // 将上传文件复制存盘
                    FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);
                    // 转存文件到指定路径,转存而不是写出,file.transferTo()有毒，谨慎使用，建议用FileUtils
                    //file.transferTo(saveFile);

                    //上传成功
                    return ResultUtil.success(urlPath + File.separator + newFileName);
                } else {
                    return ResultUtil.error(-2, "请选择有效的文件");
                }
            }
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("上传文件异常", e, UploadUtils.class);
            return ResultUtil.error(-1, "上传失败");
        }
    }

    /**
     * 上传图片（壁纸）
     *
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    public static Result<String> uploadWallpaper(MultipartFile file, HttpServletRequest request) throws Exception {
        try {
            if (file == null) {
                return ResultUtil.error(-3, "文件不能为空");
            }
            //图片类型
            String imgType = null;
            // 原文件名称
            String fileName = file.getOriginalFilename();
            // 判断图片类型
            imgType = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (imgType == null) {
                return ResultUtil.error(-3, "文件类型不能为空");
            } else {
                if ("GIF".equals(imgType.toUpperCase())
                        || "PNG".equals(imgType.toUpperCase())
                        || "JPG".equals(imgType.toUpperCase())
                        || "JPEG".equals(imgType.toUpperCase())
                        || "BMP".equals(imgType.toUpperCase())) {
                    // 写入磁盘标志
                    String success = "fail";
                    /********生成今天的日期**********/
                    String datetime = DateUtils.getCurrentDateTime(DateUtils.fileFormat);
                    //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
                    UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
                    //url路径 files/picture/{wallpaper,news}/用户名/yyyyMMdd
                    String urlPath = File.separator + "warehouse" + File.separator + "picture" + File.separator + "wallpaper" + File.separator + userSession.getUser() + File.separator + datetime;
                    //上传文件路径-/picture/目录下该用户当天的文件夹
                    String path = System.getProperty("user.home", "/home/saya") + urlPath;
                    File filepath = new File(path);
                    //判断路径是否存在，如果不存在就创建一个
                    //这里不能判断父目录getParentFile()是否存在
                    if (!filepath.exists()) {
                        filepath.mkdirs();
                    }
                    //保存的文件名
                    String imgName = RandomUtil.getRandomFileName() + '.' + imgType;

                    File saveFile = new File(filepath.getAbsolutePath() + File.separator + imgName);
                    // 将上传文件复制存盘
                    FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);
                    // 转存文件到指定路径,转存而不是写出,file.transferTo()有毒，谨慎使用，建议用FileUtils
                    //file.transferTo(saveFile);

                    //上传成功
                    return ResultUtil.success(urlPath + File.separator + imgName);
                } else {
                    return ResultUtil.error(-2, "请选择有效的图片");
                }
            }
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("上传图片异常", e, UploadUtils.class);
            return ResultUtil.error(-1, "上传失败");
        }
    }


    /**
     * @描述 删除文件
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/11/11
     * @修改人和其它信息
     */
    public static void deleteFile(String url) {
        String tempurl = System.getProperty("user.home", "/home/saya") + url;
        File file = new File(tempurl);
        //判断要删除的目录是否存在
        if (file.exists() && file.isFile()) {
            file.delete();//执行删除
        }
    }

    /**
     * 创建目录
     *
     * @param path
     */
    public static void createFolders(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (!file.isDirectory()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
            file.delete();
        }
    }

    public static void deleteFile_(String path) {
        deleteFile(new File(path));
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * @描述 获取文件的真实路径
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/19
     * @修改人和其它信息
     */
    public static File getFilePath(String url) {
        String tempurl = System.getProperty("user.home", "/home/saya") + url;
        File file = new File(tempurl);
        //判断要删除的目录是否存在
        if (file.exists() && file.isFile()) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * @描述 将url中的 \ 转换成 / e.g. \files\picture\logo\Pandora\20190109\2019010989965.png ->/files/picture/logo/Pandora/20190109/2019010989965.png
     * @参数 [url]
     * @返回值 java.lang.String
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/9
     * @修改人和其它信息
     */
    public static String descUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return "";
        } else {
            return url.replaceAll("\\\\", "/");
        }
    }

}
