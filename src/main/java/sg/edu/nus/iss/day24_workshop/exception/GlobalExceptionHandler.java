// package sg.edu.nus.iss.day24_workshop.exception;

// import java.util.Date;

// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.servlet.ModelAndView;

// import jakarta.servlet.http.HttpServletRequest;

// @ControllerAdvice
// public class GlobalExceptionHandler {

//     @ExceptionHandler(CreateOrderException.class)
//     public ModelAndView handleCreateOrderException(CreateOrderException ex, HttpServletRequest request) {
//         return new ModelAndView("error.html").addObject("errorMessage", new ErrorMessage(
//                 HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(), request.getRequestURI()));
//     }

//     @ExceptionHandler(CreateOrderDetailsException.class)
//     public ModelAndView handleCreateOrderDetailsException(CreateOrderDetailsException ex, HttpServletRequest request) {
//         return new ModelAndView("error.html").addObject("errorMessage", new ErrorMessage(
//                 HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(), request.getRequestURI()));
//     }

//     @ExceptionHandler(Exception.class)
//     public ModelAndView handleException(Exception ex, HttpServletRequest request) {
//         return new ModelAndView("error.html").addObject("errorMessage", new ErrorMessage(
//                 HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(), request.getRequestURI()));
//     }

    

// }
