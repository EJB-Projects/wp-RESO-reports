# WP RESO RU. ICEFaces Reports Tree to PrimeFaces. RELEASE №24

EJBPrimeFaces 24 (ActionListener and commandLink try)



21.05.2018 [14:08] - РЕЗУЛЬТАТ ПРЕДЫДУЩЕГО РЕЛИЗА: Значит, что сделал? Есть часть классов, они выкидывают какое-то наполняемое константными значениями (через конструктор) свинговое дерево, мой парсе TreeParse его парсит и выкидывает в jsf. 

- прикручены какие-то стили папок
- я научился детектить что это: папка или отчет и если отчет - определять родительскую папку.
- по щелчку на любой ноде открываем просто страничку viewReportForm.

ПРОБЛЕММАТИКА: мне как-то кажется не очень красивым решение открывать страничку вот так вот:

 FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "viewReportForm.xhtml");.
 
 Будем это делать через commandLink. Нужно научиться:
 
 01  |  Определять id выбранной ноды по клику                                    |  √  |  DONE 
 02  |  Получать по клику выделенную ноду типа PrimeFaces TreeNode               |  √  |  DONE
 03  |  Выкидывать Стринг - ссылку на страницу на которую будем редиректиться    |  √  |  DONE
 
 Так же - смотри файл problems.txt

 ЗАДАЧИ: В этой версии хочу попробовать натянуть один из примеров с оф.сайта PrimeFaces и попробовать таки сделать вариант через commandLink. - DONE
 
  







