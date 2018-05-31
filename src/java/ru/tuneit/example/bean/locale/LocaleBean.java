package ru.tuneit.example.bean.locale;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ArrayList;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

/**
 * @author nicola
 * @version 1.0 (Jun 20, 2011)
 */

@ManagedBean (name = "localeBean")
@SessionScoped
public class LocaleBean implements Serializable {
    private static final Logger _log = Logger.getLogger(LocaleBean.class);

    private String currentLanguage;
    
    private TimeZone timeZone = TimeZone.getDefault();
    
    private Locale usedLocale;

    @ManagedProperty("dd/MM/yyyy")
    private String dayPattern = "dd/MM/yyyy";
    
    private SimpleDateFormat dayFormat;
    
    @ManagedProperty("dd/MM/yyyy HH:mm")
    private String datePattern="dd/MM/yyyy HH:mm";
    
    private SimpleDateFormat dateFormat;
    
    @ManagedProperty("dd/MM/yyyy HH:mm:ss")
    private String dateTimePattern;
    
    private SimpleDateFormat dateTimeFormat;
    
    @ManagedProperty("HH:mm")
    private String timePattern;
    
    private SimpleDateFormat timeFormat;

    private static final ArrayList<SelectItem> AVAILABLE_LOCALES = new ArrayList<SelectItem>(2);
    static {
	AVAILABLE_LOCALES.add(new SelectItem("ru","Russian"));
        AVAILABLE_LOCALES.add(new SelectItem("en","English"));
    }

    public LocaleBean() {
    }

    @PostConstruct
    public void init() {
        currentLanguage = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
        usedLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        dateFormat = new SimpleDateFormat(datePattern);
        dateTimeFormat = new SimpleDateFormat(dateTimePattern);
        dayFormat = new SimpleDateFormat(dayPattern);
        timeFormat = new SimpleDateFormat(timePattern);
    }

    public void changeLanguage(ValueChangeEvent event) {
	try {
	    FacesContext ctx = FacesContext.getCurrentInstance();
	    Locale locale = ctx.getViewRoot().getLocale();
	    String newLanguage = (String) event.getNewValue();

            if ("en".equals(newLanguage) || "ru".equals(newLanguage))  {
		setCurrentLanguage(newLanguage);
	    }

            if (!getCurrentLanguage().equals(locale.getLanguage())) {
		usedLocale = new Locale(getCurrentLanguage());

                ctx.getViewRoot().setLocale(getUsedLocale());
	    }
	} catch (Exception e) {
	    _log.error("Error change locale. : " + e.getMessage(), e);
	}
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

    public ArrayList getAvailableLocales() {
        return AVAILABLE_LOCALES;
    }

    public Locale getUsedLocale() {
        return usedLocale;
    }

    public String getDatePattern() {
	return datePattern;
    }

    public void setDatePattern(String datePattern) {
	this.datePattern = datePattern;
    }

    public String getDateTimePattern() {
        return dateTimePattern;
    }

    public void setDateTimePattern(String dateTimePattern) {
        this.dateTimePattern = dateTimePattern;
    }

    public String getDayPattern() {
        return dayPattern;
    }

    public void setDayPattern(String dayPattern) {
        this.dayPattern = dayPattern;
    }

    public String getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public SimpleDateFormat getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(SimpleDateFormat dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public SimpleDateFormat getDayFormat() {
        return dayFormat;
    }

    public void setDayFormat(SimpleDateFormat dayFormat) {
        this.dayFormat = dayFormat;
    }

    public SimpleDateFormat getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(SimpleDateFormat timeFormat) {
        this.timeFormat = timeFormat;
    }

}