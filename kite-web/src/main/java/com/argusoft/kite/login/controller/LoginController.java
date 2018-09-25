package com.argusoft.kite.login.controller;

import com.argusoft.kite.algo.dto.DoubleUpAlgoDto;
import com.argusoft.kite.algo.dto.SeaWaveAlgoDto;
import com.argusoft.kite.algo.service.DoubleUpAlgoService;
import com.argusoft.kite.algo.service.SeaWaveAlgoService;
import com.argusoft.kite.instrument.model.InstrumentDetailEntity;
import com.argusoft.kite.instrument.service.InstrumentService;
import com.argusoft.kite.login.service.LoginService;
import com.argusoft.kite.order.constant.OrderConstantUtil;
import com.argusoft.kite.order.service.OrderService;
import com.argusoft.kite.ticker.dto.Tiker;
import com.argusoft.kite.ticker.dto.TokenDetailDto;
import com.argusoft.kite.ticker.service.TickerService;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hshah
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private KiteConnect kiteSdk;
    @Autowired
    private TickerService tickerService;
    @Autowired
    private Tiker kiteTicker;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeaWaveAlgoService seaWaveAlgoService;
    @Autowired
    private DoubleUpAlgoService doubleUpAlgoService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void retrieveBoxDetail(@RequestParam(name = "request_token") String requestToken,
            @RequestParam(name = "status") String status) throws KiteException, JSONException {
        try {
            loginService.setRequestToken(requestToken, "nd5f2jkv5xxsqmkrifmhvndemq3vhp9e");
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void retrieveBoxDetail() throws KiteException, JSONException {
        instrumentService.insertDaysOHLC();
    }

    @RequestMapping(value = "/startTicker", method = RequestMethod.GET)
    public void startTicker() throws KiteException, JSONException {
        tickerService.configure();
        kiteTicker.getKiteTicker().connect();
    }

    @RequestMapping(value = "/placeOrder", method = RequestMethod.GET)
    public void placeOrder() throws KiteException, JSONException {
        InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(895745);
        orderService.placeRegularOrder(OrderConstantUtil.TransactionType.BUY,
                OrderConstantUtil.OrderType.MARKET, 1, OrderConstantUtil.Product.MIS, 0, 0, instrumentDetailEntity);
    }

    @RequestMapping(value = "/subcibe", method = RequestMethod.GET)
    public void subcribe() throws KiteException, JSONException {
        InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(895745);
        List<TokenDetailDto> tokenDetailDtos = new LinkedList<>();
        TokenDetailDto tokenDetailDto = new TokenDetailDto();
        tokenDetailDto.setToken(895745l);
        tokenDetailDtos.add(tokenDetailDto);
        tickerService.subscribe(tokenDetailDtos);
    }

    @RequestMapping(value = "/unsubcibe", method = RequestMethod.GET)
    public void unsubcribe() throws KiteException, JSONException {
        InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(895745);
        List<TokenDetailDto> tokenDetailDtos = new LinkedList<>();
        TokenDetailDto tokenDetailDto = new TokenDetailDto();
        tokenDetailDto.setToken(895745l);
        tokenDetailDtos.add(tokenDetailDto);
        tickerService.unSubscribe(tokenDetailDtos);
    }

    @RequestMapping(value = "/subcribealgo", method = RequestMethod.POST)
    public void subcribealgo(@RequestBody SeaWaveAlgoDto seaWaveAlgoDto) throws KiteException, JSONException {
        seaWaveAlgoService.subcribeToken(seaWaveAlgoDto);
        InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(seaWaveAlgoDto.getToken());
        List<TokenDetailDto> tokenDetailDtos = new LinkedList<>();
        TokenDetailDto tokenDetailDto = new TokenDetailDto();
        tokenDetailDto.setToken(seaWaveAlgoDto.getToken());
        tokenDetailDtos.add(tokenDetailDto);
        tickerService.subscribe(tokenDetailDtos);
    }

    @RequestMapping(value = "/subcribedoubleup", method = RequestMethod.POST)
    public void subcribealgo(@RequestBody DoubleUpAlgoDto doubleUpAlgoDto) throws KiteException, JSONException {
        doubleUpAlgoService.subcribeToken(doubleUpAlgoDto);
        InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(doubleUpAlgoDto.getToken());
        List<TokenDetailDto> tokenDetailDtos = new LinkedList<>();
        TokenDetailDto tokenDetailDto = new TokenDetailDto();
        tokenDetailDto.setToken(doubleUpAlgoDto.getToken());
        tokenDetailDtos.add(tokenDetailDto);
        tickerService.subscribe(tokenDetailDtos);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void subcribealgo() throws KiteException, JSONException {
        instrumentService.test();
    }

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public void test() throws InterruptedException {
//        final FirefoxProfile firefoxProfile = new FirefoxProfile();
//        firefoxProfile.setPreference("xpinstall.signatures.required", false);
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Harshit\\Downloads\\geckodriver-v0.22.0-win64\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("https://kite.zerodha.com/connect/login?api_key=3579298mc3xenf4c");
        Thread.sleep(1000l);
        List<WebElement> someElements = driver.findElements(By.cssSelector("input"));

        // Now iterate through them and check for our desired match
        for (WebElement anElement : someElements) {
            System.out.println("anElement.getAttribute(\"type\") ::" + anElement.getAttribute("type"));
            if (anElement.getAttribute("type").equals("text")) {
                anElement.sendKeys("YI9119");
            } else if (anElement.getAttribute("type").equals("password")) {
                anElement.sendKeys("harshit21@");
            }
        }
//        WebElement username = driver.findElement(By.linkText("User ID"));
//        username.sendKeys("YI9119");
//        WebElement password = driver.findElement(By.linkText("password"));
//        password.sendKeys("harshit21@");

        WebElement login = driver.findElement(By.className("button-orange"));
        login.click();
        Thread.sleep(1000l);
        someElements = driver.findElements(By.cssSelector("input"));
        for (WebElement anElement : someElements) {
            System.out.println("anElement.getAttribute(\"type\") ::" + anElement.getAttribute("type"));
            if (anElement.getAttribute("type").equals("text")) {
//                anElement.sendKeys("YI9119");
            } else if (anElement.getAttribute("type").equals("password")) {
                anElement.sendKeys("1");
            }
        }
        login = driver.findElement(By.className("button-orange"));
        login.click();
        Thread.sleep(10000l);
//        System.out.println(driver.getPageSource());
//        driver.close();
    }

    @PostConstruct
    public void fillKiteSdkFromDb() throws KiteException, IOException {
        kiteSdk.setUserId("YI9119");
        loginService.fillKiteSdkFromDB();

//        instrumentService.insertAllInstrumentDetail();
    }

}
