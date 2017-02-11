package com.limagiran.hearthstone.util;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Vinicius Silva
 */
public class PopUpWorker extends JDialog implements PopUpWorkerUtils {

    private static final String MSG_CONFIRM_CANCEL;
    private static final ImageIcon DEFAULT_ICON;
    //<editor-fold defaultstate="collapsed" desc="ImageLoading">
    private static final String IMAGE_BASE_64 = "R0lGODlhIAAgAKUAAASGvITC3MTm7E"
            + "SmzKTS5CSWxOTy9GS21LTe7BSSvJTO5NTq9LTa7HS+3BSOvJTK5Kza7PT6/AyOvI"
            + "zK3Mzm7FSuzKzW5DSexAyKvIzG3Oz2/Gy21Lze7BySvJzO5Hy+3Mzq9ASKvITG3E"
            + "yqzKTW5CyaxOTy/Nzu9Pz+/Mzm9Fyy1KzW7DyizGy61Lzi7ByWxJzS5HzC3P///w"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH/C05FVFNDQV"
            + "BFMi4wAwEAAAAh+QQJBgAyACwAAAAAIAAgAAAG/kCZcEgcGkAMC+KEKjqfzkgTZb"
            + "K0ChVSE8rtRjydDmzbLRcjBwDLYG4TLZgOyE3nhCQUupsAcCz0bSMABRGAZScAIR"
            + "+GZYIYGoxcMAAACpFQC5Qjl08RlAmcT5QAkBoHJSwMl56UCyglowCqjJmUFBCxap"
            + "F8lAYZuSGFhiqUISgIuQAwhhEYlBcyESG5JWRuv5QNQhXJHnoGEqN5Mi7JGH9tKA"
            + "Oj0EPruS8nZihpo7NCC9O5DghdGoKjBjh5kIxSBQFONGRwECsBGyctClJ6oeJDjB"
            + "bvYoVA+ARFRIkgAWBwUQaGs5C5CqA7NEJfSAwxhLlZcCCBxBIiIDFCsYBECoAGMT"
            + "JYeBiKURAAIfkECQYAMgAsAAAAACAAIACFBIa8hMLcxOLsRKbMpNLkZLLU5PL0JJ"
            + "bEdL7UlM7k1Or0tNrsHJK8lMrkzOr0VK7MdLrUFI68jMrczObsrNrsbLrU9Pr8NJ"
            + "7EfL7cDIq8jMbcrNbsbLbUnM7kvN7sXLLUBIq8hMbcxObsTKrMpNbkZLbU7Pb8LJ"
            + "rEdL7c3O70tN7sHJbEVK7UzOb0/P78PKLMfMLcnNLk////AAAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABvtAmXBILAotFqNyyZRZHBtRc9r0sD"
            + "zUbNHyeZi0YBnqkAprC4yyeWo5nZLrpgNQiU8LgMkWblcABkMGLwAAH3xrJwAOMi"
            + "6JhAAldjIIdDIej4QZLnYdACsyCZgAIJtxFIQGIaIMkhuEHhqrkqEAKrSPGZIfhA"
            + "IqogBqZi4RhCkmvzGmmUKOj4DCF4QjQqqiUmENj8kyJiCiF6VZChmEEXwVv5FZKS"
            + "uPEkQmDOnhSxPyhAf0lr8AL4tKFig9AqFASTZ+A2IosODCAAUO5DBRYHKQn0VCIE"
            + "hMWUDs4i8GWKiYKOFxYIUvYFIguPcRQbA1CkjAQFABBomCknIGAQAh+QQJBgAwAC"
            + "wAAAAAIAAgAIUEhryExtxEpszE4uwklsRkstTk8vSk0uR0vtQUkrzU6vS02uwUjr"
            + "zM6vQ0nsR0utT0+vwMjryUzuRUrszM5uxsutR8vtwMiryMxtwsmsRsttTs9vys1u"
            + "Qckry83uwEirxMqszE5uwkmsRkttTk8vyk1uR0vtzc7vS03uw8osz8/vxcstTM5v"
            + "R8wtyMytwclsT///8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAG/kCYcEgsGo/IpHLJRKo2zSgRslBJpRAMNGm9CiGrbZFDABAWXp"
            + "hC0B2iAHB4I10JFB1xOCJtuJyGA3lwFWkwIAVDI4IAKIUHH1tleRmFMAYABzAbiy"
            + "6VMBGIFIsKnhkOMG95H21pDhcwC4IJnjBlEIF5HZ4qHwAGl6qsVyFwUAyCpIUIAB"
            + "9CE4KdaRvHKUIlghnCTcsAdjAQCYKZVx5wHwZDLoIMf1EUEXAjUxnY6EwL8AARYk"
            + "IUvXkvBijZUCFPiSPXFq2gIEyBhXxwWiRJuCgBiBUjQLxYtEeJh3CLQsKJMG7Jhh"
            + "UiF4FoF0VBhWMhI4ygUElFCAkVRqyoIGGABTZaUoIAACH5BAkGADIALAAAAAAgAC"
            + "AAhQSGvITC3MTi7ESmzKTS5CSWxGSy1OTy9JTO5BSOvLTa7HS61NTq9JTK5FSuzD"
            + "SexPT6/AyOvIzK3Mzm7KzW5Gy61AyKvIzG3CyaxGy21Oz2/JzO5BySvLze7Hy+3F"
            + "yy1ASKvITG3MTm7EyqzKTW5CSaxGS21OTy/BSSvLTe7HS+3Nzu9FSu1DyizPz+/M"
            + "zm9KzW7JzS5P///wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAb+QJlwSCwaj8ikclnUMCDMqBDyAQASFOkSMrBaQStt0uH1SqQQ17FR9iK0DO"
            + "PB0gZEDlpIp5ipW/ZiBBpDGiB1KWJCGgFDG3UqiUMjajIjbRxQkTILiDIcbWeaMh"
            + "ILMhp1eKIxAzIrbSWiQgQFMgxtDrEyCAkyB20muQsoMhCGXh+5AxhCGGUtscW4Mh"
            + "VlFpmRMQAhQh1tJJouBQAiQuFlGJRiFwAo6Y5loVoidJBDLs1eIIBREwkAIKmGMD"
            + "D25dsSFw3oAIhHhEQdB3GOuFDwwIuDdEXetWkRQsEEBh02fEBRZpKSFP7q5KuzAC"
            + "OSA2RU1inRScqEDwpVtiDhEk0OihAfRoz4oIJEwFyRggAAIfkECQYALwAsAAAAAC"
            + "AAIACFBIa8hMbcxOLsRKbMpNLkJJbE5PL0ZLLUlMrkdL7UHJK81Or0tNrsNJ7E9P"
            + "r8FI68zOr0VK7MdLrUnNLkDIq8jMbczObsrNbkLJrE7Pb8bLrUnM7kfMLcvN7sPK"
            + "LEBIq8TKrMpNbkJJrE5PL8ZLbUlM7kdL7cHJbE3O70tN7s/P78XLLUjMrczOb0PK"
            + "LM////AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAABvfAl3BIfC0WxaRy+VKVRAAABsmsEi2YqFakslpZ2jAA4mVyxGFquQhGRz"
            + "HdNVHgjnoMcqIKivYI8kUIbgFxgEIqJ2ghhk1DHWgsjC94QhpiHpJNVA1iFpkvDE"
            + "IUYQ2fLyUvGWIBpgmTYimmEamdnw6lLx9hnpkCLkKcWqGZCSSVYRyZKgobQo9aGJ"
            + "khAJTLYbGGDgUDRILQDoYJAB1EDnxRK4VlBADcRQK6WulrEwAfKEreWg28TBkrUV"
            + "AtCYAGxAVwRVBwGAVAgxUC8bToahCBxIoBibQ49ILCQx00FCbkSTHgIwAFATIwMh"
            + "CCxIAGIihyEKDOlJwgACH5BAkGAC8ALAAAAAAgACAAhQSGvITC3MTi7ESmzKTS5C"
            + "SWxOTy9GSy1BSSvLTa7JTO5NTq9HS+1BSOvKza7DSexAyOvMzq9Fyu1KzW5PT6/A"
            + "yKvJTK5Mzm7Gy61BySvLze7JzO5HzC3DyixASKvIzG3EyqzKTW5CyaxOz2/GS21L"
            + "Te7Nzu9HS+3Fyy1KzW7Pz+/Mzm9ByWxJzS5DyizP///wAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAb5wJdwSHxRHAzQQ3SiFJ"
            + "9QYkTiAVivD1V0KxyhruBrghsVIMJoC/kZQrsFa2LJjcbEh6YGvTIgcTR3QwNuDx"
            + "NOgUQOaBAEiFAPYRkLjk8rYRAmlE8cYROaTx1gHZ9PEGCepEMUYB6HpFoGYA+pQg"
            + "YvI2AgtC+TKrm7JUIFWLQqCkIkV62pGmMvE2AhqSCHFGdWs58JKEQBYC2aIxkXRB"
            + "QZVxCTiBQd3EVzV5KBKiAZI1CcVw3OZAYdHnCiYAgjQR0UYw088IvyoQqYAS0yCa"
            + "FwgcM5BAHJCCjgkFWGa1YG2LpjTAQdKwWkaRLAYUAGCBUQPCCRQMsuR0EAACH5BA"
            + "kGADAALAAAAAAgACAAhQSGvITC3MTi7ESmzKTS5CSaxOTy9GS21BSSvJTO5LTa7N"
            + "Tq9HS61BSOvFSuzKza7PT6/AyOvJTK5Mzq9KzW5DSexAyKvIzG3Mzm9Oz2/Gy21B"
            + "ySvJzO5HzC3Fyu1ASKvITG3MTm7EyqzKTW5CyaxOTy/Lze7Nzu9HS+3Pz+/KzW7D"
            + "yizGy61ByWxJzS5Fyy1P///wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAb2QJhwSBSeKB3WSwNSZYrQqDADIgGuWOxqIpWmApasGN"
            + "swdIuLynh97ZyHggibzXrDJuH5mvI2bNgIKw4DJHkAL3YiYxEMXEQpJi4hdgpjDm"
            + "Z2Z2pZIJlvGGJunmcoWRUpo5pZk6leH1gkrV0GWaKyUCdZfLe4WSa8UBC+wFB/Vy"
            + "7ERQ5YB8kwqDAuWBvQvBnQGYa7vBhELFgF1a0mmEIGclcotwYXUNJYI60GIhBRL1"
            + "kEoxMkC1IQilhYiIsCAcQGVv7ugRsxUAgECS0K9LOTAN2VBi8uqFDgIsCAVyzqeT"
            + "LAwtAiFiduQRjxosCHD4FYKGjoLFMQACH5BAkGADEALAAAAAAgACAAhQSGvITC3M"
            + "Ti7ESmzKTS5CSWxOTy9GS21NTq9LTe7BSOvIzK3FSuzDSexHS+1Mzq9PT6/Mzm7L"
            + "Ta7Fyu1AyKvIzG3KzW5CyaxOz2/Gy21Lze7BySvJTK5DyixHzC3ASKvITG3MTm7E"
            + "yqzCSaxOTy/Nzu9BSSvFSu1HS+3Pz+/Mzm9Fyy1KzW7Gy61Lzi7JTO5DyizP///w"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAb+wJhwSB"
            + "RCBIRF5SUxFJ/QIYQw+ACuWEDBgYhGU69NdpxllLxEQ4PMxlJe6FhI0a5fPV4VxW"
            + "7/QKAGJmwjKw4tKxd1D1AiYx8tXUUGIIJZHxhPBGMNZ14QDlkgTymJWAN/cTECBw"
            + "cSUC5ZI5ipqQdZIbS0I6a5qRBZFr1xCFmzwlERWCbHaMlXI8xeJVgF0V9WABQp1l"
            + "AdWJHcRAFYHOFF01cN5kWNVy7rQw9YDdvwMS1YeMyoRRBrVwSOleAnadcVOLRSWO"
            + "gUBcM/ACIYRtHQwkmcFKCwMLBgLMYRDw1A1KOFgAG2KwoKFNhjwkPHXhASgDjAQI"
            + "SIAy9CjLSXKwgAIfkECQYALwAsAAAAACAAIACFBIa8hMLcxOLsRKbMpNLkJJbE5P"
            + "L0ZLbUFJK8lM7ktNrsVK7MFI68lMrk1Or0rNrs9Pr8DI68jMrcrNbkNJ7EdL7cXK"
            + "7UDIq8jMbczObs7Pb8bLbUHJK8nM7kBIq8hMbcTKrMpNbkLJrE5PL8vN7sVK7U3O"
            + "70/P78rNbsPKLMXLLUzOb0bLrUHJbEnNLk////AAAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABv7Al3BIFJ4cihDh4YAUn9DhKb"
            + "S4AK7Yy8DljEJPCQ52TGZ8Tl6iiUJukwuZ9IsUcduvHs/ES7Le/wAEUCZ1Yx4LLg"
            + "IOAiEsCHYeAkUnbGMVGl8dj20cmEMdYwwkchogHm0VUmJXEXFyLycHbRRDKGMhr1"
            + "IDZANDC1gpuUQGDLdDmwCjwkMZKQAcCUMGWAhoy2kkWAvXcgpYH9xp3lcu4V4CWO"
            + "XmUNNXEutRqyzwUCpXtPRF4wAG+UQnBU5h8EfkwRUEXQi+AHHlgEIhGlZFe+hg1b"
            + "uHBpwBGGAil7VXIVoA8LBhRZQjnoSdmDDgVAsLHwgkwSDhQUJuEBQEOKCCRQeAEC"
            + "MecgsCACH5BAkGADMALAAAAAAgACAAhQSGvITC3MTi7ESmzKTS5CSWxGSy1OTy9B"
            + "SSvLTe7JTO5HS+1NTq9BSOvFSuzLTa7DSexPT6/AyOvJTK5Mzm7Kza7Gy61AyKvI"
            + "zG3KzW5CyaxGy21Oz2/BySvLze7JzO5HzC3Fyy1ASKvITG3MTm7EyqzCSaxGS21O"
            + "Ty/HS+3Nzu9FSu1DyizPz+/Mzm9KzW7ByWxLzi7JzS5P///wAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAb9wJlwSBS2GInMK3YoOp/EiGxwAVivCE"
            + "OiBYW2Ro2reKypdIsu03g9LnHOs0qVTbfCGN0Mu8MqsRB1DXhOLiJiFyCDQwwLEn"
            + "tNUWpXbl0HJWwlRSNiC3BCIGwEQxFzACeeQxZrJlwzE1caEahGEGsPQixXHrNDJG"
            + "sOMxxXELxEuWISLTFXCsVDBGsqMlcqzkIHaxybAAjWQwNiwNss3kIcDiIiIW/TAJ"
            + "nlRrJCAlYD8E8RVhr3T8dv/EQUWMkAMEoHAOQKDqlgZZdCIQsAFPinsEUIAANaVT"
            + "zhTt5DDCJMUHgohAE4CxQVxgjRwMCDlABbkJARQEEMBgc0kkQVBAAh+QQJBgAyAC"
            + "wAAAAAIAAgAIUEhryMxtxEpszM5vSs1uQklsRkttTs9vwUkryc0uTE4ux0utTc7v"
            + "Q0nsQUjryczuRUrsy83uwMjryUzuS03ux8wtwMiryUyuTU6vS02uwsmsRsttT8/v"
            + "wckryk0uR0vtTk8vQ8osRcstQEiryMytxMqszM6vSs1uwkmsT0+vzE5uxUrtRsut"
            + "QclsSk1uR0vtzk8vw8osz///8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAG/UCZcEgUcgaEhCsCKjqfxAMpNgJYrx2DAspNVSzXsLix5RIjLb"
            + "FabEiZZZO1PNxoQi/qEomiikwgVWooB08ZYiyETgcfgXQcRSAOWCZvCghqH0UiVx"
            + "qJbxiXYhhDGFh2b0IRahBDG1cRqJqNVgwyHBJWIrFFDGoBMiZWI7W7RCFiAjIJVi"
            + "XFRQFiEjIVVgnORBRqICxWo9dCwaLcAG7fwGomJFaP5qpiDApWnteGYikckirmMg"
            + "e4V6wyuP3al8FfiEQMRjTYJyQFhQFFXJVhqMhBDHYUixAAMCHjky8QPRap0IGYyC"
            + "EeCug7OQSEgQoYWWKYEGEeSxgHYrL8FgQAIfkECQYALgAsAAAAACAAIACFBIa8hM"
            + "LcxOLsRKbMJJbEpNLk5PL0ZLbUFJK81Or0tNrsFI68nM7kzOr0NJ7ErNrs9Pr8dL"
            + "7UDI68zObsVK7MrNbkDIq8lMrk7Pb8bLbUHJK8PKLEBIq8jMrcxObsTKrMLJrEpN"
            + "bk5PL83O70vN7snNLk/P78fMLczOb0XLLUrNbsbLrUHJbEPKLM////AAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABv5Al3"
            + "BIFGJIpQ7jkTAVn9Dh6EQAWK+ARYoU7RpS2DDWwe0SSxax+rqCmE0rMYfgIEjWG0"
            + "zUdMBaDgpuQiYTJwtiDoJFJ1gHelEYFGIfTkQoVwsKZkMdYh1FG1YaCZtEjH4GQw"
            + "pWFqSlQyahWBFDH1Ylr0UCYRJuEFYOuU8OYQ8uuwAVwothKy4hAL3LRBVhwSUAA9"
            + "NEE2ELzwDO20INYi6rJ+NCJOYjAOnqnVjfJn/qLpJjQgMb6hgcYQ4IYcBB0bIAYk"
            + "IYkaBsWxUsBYec+DCORUAiEAi4WhbnCocRuihOg2ALAIKGRQpcGDfCg8GUZe5FmY"
            + "BCZhcIIirZ3Mmz5wK2IAAh+QQJBgAxACwAAAAAIAAgAIUEhryEwtzE4uxEpsyk0u"
            + "QklsTk8vRkstSUzuQUkry02uzU6vRUrsw0nsSUyuT0+vwUjryMytzM6vSs2uxsut"
            + "RcrtQMiryMxtzM5vSs1uQsmsTs9vyczuQckry83uw8osQEiryExtzE5uxMqswkms"
            + "Tk8vxkttS03uzc7vRUrtT8/vx0vtxcstSs1uyc0uQclsQ8osz///8AAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAG/sCYcEiMqTCuSCDEET"
            + "yK0KhQdUpZANgsADRSqKRSj0ZL1mpOYKKKUm5rWU/wZqAFfVYIF2L1AZUbG1IPMF"
            + "kaLoFQGwFXZnFFJlgWCF9pBh9kI1ACWB0LaUQPI2QIRXQJKJ9FDyRaEIgxKFgTqZ"
            + "pkAUMhAAy0UXRZHZR0nrxFBGQCQhANxFAGtjEbALfMRYxYmRgAaNRECVrLIgCo3E"
            + "PWAAUxEgCv3M5aJNDr5EIEflnLMR3j5AxkLEIskJGLRSaCkAwO5okiM2wNOQRlNB"
            + "AhUIJahjYu1LRgFq5MAUdCDFTk1a/MtiIbKKUiRGYFGJWfApBhAHPegz5YWNScZ8"
            + "SDCQsJPIMKHUorCAAh+QQJBgAyACwAAAAAIAAgAIUEhryEwtzE4uxEpsyk0uQkls"
            + "Tk8vRkttQUkryUzuS83uzU6vR0vtQUjryUyuRUrsy02uw0nsT0+vwMjryMytzM5u"
            + "ys2uwMiryMxtys1uTs9vxsttQckryczuR8wtxcstQ8osQEiryExtzE5uxMqswsms"
            + "Tk8vy84uzc7vR0vtxUrtT8/vzM5vSs1uxsutQclsSc0uQ8osz///8AAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAG/kCZcEiUoWCew+cQaBmK0K"
            + "hQkogArlhsBCaRSgmcrDjLgXmJq4+YUyohxleS5rxSXS8HRXeogXwuYgVPUilXB4"
            + "NSGi6BiEQKACEZZ0QnE1kgK0USLwAKk0ULllgURRgACZ9QEFkTc1MIA6lRD1keQx"
            + "kAC7JQFWSZMh8ku1EFWQJCBRbDUGpYIjISF3vLQx5ZHzIGsdREIpdGLtxEDFkRMi"
            + "bP4kID5TIrpOoSYsIyLeoyCWIMQiPxYVmSsqk7ICaEK2jcHIx5cG+FtzH9xC2IAY"
            + "ceNRQk4ACY0GiXhn9wAlKjoPGUOmtjQphRd2JMgYj3yF1BQOHXPSELYGSweXNYAR"
            + "AAIfkECQYALgAsAAAAACAAIACFBIa8hMbcRKbMxOLsZLbUJJbEpNLk5PL0dLrUFI"
            + "68VK7MNJ7EvN7slMrk1Or09Pr8DI68tNrsfMLcXK7UDIq8jMbczOr0bLbULJrErN"
            + "bk7Pb8dL7UPKLEBIq8TKrMzObsJJrEpNbk5PL8HJK8VK7UvOLsnM7k3O70/P78XL"
            + "LUjMrcbLrUdL7cPKLM////AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAABvtAl3BIdFkqKYGAMwkMUMWoVBhZAK5YbEKimU"
            + "4Pgqw4SwlAvUNGAgAhGCyH08Ck6IgXB7TLwK48phoBEFkjDl4hABwiei4HHoQnUg"
            + "4UKWeMKBtZGJZCKBgKnIwuK1kSRSYLf6JEKFZXHXmdGIarRRZZK0MDLLVSYVcQZ1"
            + "y9USZ2VwxCKsRRJ6VUzEUoWQouD7TRQ1kc1qrZLhpZC9bfQyXb5UQI1OlCD2tYpu"
            + "0SYhHtA8fAocwW8FgE6QxQGBMpW4lfYnL1chCiAYECY66M8HYpRUQxED70anAxS4"
            + "dkvR51BDBiADMFIxV0YTbgogCT3zKAkOhBRax2FBkFAQAh+QQJBgAyACwAAAAAIA"
            + "AgAIUEhryEwtxEpszE4uwklsRkstSk1uTk8vR0vtQUkrzU6vQ0nsS02uwUjryUyu"
            + "RUrsx0utT0+vwMjryMytzM5uxsutR8vty84uwMiryMxtwsmsRsttTs9vwckrw8os"
            + "S83uxcstQEiryExtxMqszE5uwkmsRkttSs1uTk8vx0vtzc7vS03uxUrtT8/vzM5v"
            + "R8wtwclsQ8osz///8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAG/kCZcEgUKiYFwUJgMnCK0KgQ9SIArljsYyCVRhAhgAaEeCFGiSzggeoSXY"
            + "SGRQVtXUbZDsktWzVeEXwrHVgYK10fGgp8QwcLhXtQByNPjEMRj1cwlUQpnJZCHG"
            + "lXJkUDdKBFDFkURJGpRQJYIEMcgbBFK1ghty24dRhYJ79deFcVxFIVWALJUQhYGs"
            + "5QG1gl00Ue0di1YVcj3EIOWQjhEaNXDOEgWRi3zi0QaqXTHA9qIahuKArvQygiGq"
            + "gBUM5NBHYAJIzY8CKACW0DxfiLYiGixSsJDjAqcTFigkWMNHRUo0Efnwwjr4QI4A"
            + "tUi2UXG1TQ+IuDgQpKNHh4IOJCBctwsIIAACH5BAkGAC0ALAAAAAAgACAAhQSGvI"
            + "TC3EyqzMTi7KTS5CSWxGy61OTy9BSOvJTO5Lze7Hy+3JTK5Fyu1LTa7DSexAyOvI"
            + "zK3NTq9KzW5HS61PT6/GS21AyKvIzG3CyaxBySvGSy1HS+1ASKvITG3Mzm9KTW5C"
            + "SaxOz2/BSSvJzS5Lzi7HzC3Fyy1DyizNzu9KzW7Pz+/HS+3P///wAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAb8wJ"
            + "ZwSBSKHImAKUIYVIrQqDHyAFiv1o5gspJGV5iChfRJVQ4S0AlyfUi8xMNJ1ZVWAh"
            + "frBQRvpet9KRlXEV4iKX1FFShXJF8iiVAiGh0AHW+RkQNXKJmZDVcOnokflQCdo3"
            + "1VVoipXiZXDK5eDlcns1ISbbhRKVcavFAfVyPBRRO/xkQGnMpCKyNXFs4tCVh8yg"
            + "cIVx2QUAMRDk+JIqtWt1DMVhmYXoJYAO1DJfAdFq1FIiZ5WAZRAfCuZODAAASBAA"
            + "ICAggxrggJhRADIsCnSEPEiyPkQUlh7iIWFAcSrfDAzyOECIDIeQgRMUMEb64kkO"
            + "BwoiYHAhSpZQoCACH5BAkGAC8ALAAAAAAgACAAhQSGvITC3MTi7ESmzKTS5CSWxO"
            + "Ty9GS21JTO5HS61BSOvNTq9LTa7JTK5DSexPT6/IzK3Mzq9FSuzHzC3AyOvIzG3M"
            + "zm7KzW5CyaxOz2/Gy21JzO5Hy+3BySvLze7AyKvITG3EyqzCSaxOTy/HS+3BSSvN"
            + "zu9LTe7DyizPz+/Fyy1Mzm9KzW7Gy61JzS5P///wAAAAAAAAAAAAAAAAAAAAAAAA"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAb8wJdwSBSmVhdEgyB4FJ/QYY"
            + "oBapEmmkEJAEAhnNGooQldTDqADykTLoLbKcgHoCC071CTiEvC+4cZGFwTf38GFF"
            + "wehX4uXB1vi1EpewAVkXcNXCUpl2EmXAAnnWFbAC2jUQNcKKhQEpqtT69cnLFDKK"
            + "C1ti9oXLtCn1wiRBsoKA26hROgEkPLoKeLD70ACEZzoAAMi89cBkIj2VwUFn8e2S"
            + "FSpdkUince2FwCRI3iXC3fUA8k4iqy9lw+HGCQ78GJBOscsXHjAKC4eOIoLIiSAZ"
            + "fDiwBKlAvz4ABGgA5M+PEg6CMAChCS3ZkSAhFICJCkeUCQoEUCEAR/XQoCADs=";

    //</editor-fold>    
    static {
        if (Locale.getDefault().toString().equalsIgnoreCase("pt_BR")) {
            MSG_CONFIRM_CANCEL = "Cancelar?";
        } else {
            MSG_CONFIRM_CANCEL = "Cancel?";
        }
        DEFAULT_ICON = new ImageIcon(DatatypeConverter.parseBase64Binary(IMAGE_BASE_64));
    }

    private JLabel l;
    private final JProgressBar progress = new JProgressBar();

    private PopUpWorker(MySwingWorker worker, ImageIcon icon, boolean cancellable, boolean undecorated) {
        super(worker.owner);
        init(icon, worker, cancellable, undecorated);
    }

    private void init(ImageIcon icon, MySwingWorker worker, boolean cancellable, boolean undecorated) {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        worker.setupPopup(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (cancellable && confirm(PopUpWorker.this, MSG_CONFIRM_CANCEL)) {
                    worker.cancel(true);
                    setVisible(false);
                }
            }
        });
        //setTitle("");
        //setIconImages(null);
        setModal(true);
        setResizable(false);
        setType(Window.Type.POPUP);
        setUndecorated(undecorated);
        Border border = new CompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1), new LineBorder(Color.GRAY, 1));
        getRootPane().setBorder((undecorated ? new CompoundBorder(border, new EmptyBorder(6, 12, 6, 12)) : null));

        JPanel panel = new JPanel(new BorderLayout(0, 5));
        l = new JLabel("  " + worker.msg, icon, SwingConstants.CENTER);
        l.setIconTextGap(16);
        l.setFont(new Font("Arial", Font.PLAIN, 28));
        if (!undecorated) {
            l.setBorder(new EmptyBorder(10, 10, 10, 10));
        }
        panel.add(l, "Center");
        panel.setBorder(new EmptyBorder(4, 2, 4, 2));
        progress.setVisible(false);
        progress.setStringPainted(true);
        panel.add(progress, "Last");
        getContentPane().add(panel);

        InputMap inputMap = getRootPane().getInputMap(WHEN_IN_FOCUSED_WINDOW);
        getRootPane().setInputMap(WHEN_IN_FOCUSED_WINDOW, inputMap);
        inputMap.put(KeyStroke.getKeyStroke(VK_ESCAPE, 0), "esc");
        getRootPane().getActionMap().put("esc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cancellable && confirm(PopUpWorker.this, MSG_CONFIRM_CANCEL)) {
                    worker.cancel(true);
                    setVisible(false);
                }
            }
        });
        pack();
        setLocationRelativeTo(null);
    }

    public void setMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            try {
                l.setText(message);
                pack();
                setLocationRelativeTo(null);
            } catch (Exception e) {
            }
        });
    }

    public void setProgress(int value) {
        SwingUtilities.invokeLater(() -> {
            progress.setValue(value);
            if (!progress.isVisible()) {
                progress.setVisible(true);
                pack();
                setLocationRelativeTo(null);
            }
        });
    }

    public static void exe(MySwingWorker worker) {
        exe(worker, false);
    }

    public static void exe(MySwingWorker worker, boolean cancellable) {
        SwingUtilities.invokeLater(() -> new PopUpWaitingBuilder().worker(worker)
                .cancellable(cancellable).undecorated(!cancellable).create().setVisible(true));
    }

    /**
     *
     * @author Vinicius Silva
     */
    public static abstract class MySwingWorker extends SwingWorker<String, String> {

        private PopUpWorker popup;
        public final Window owner;
        public final String msg;

        abstract public void finish();

        public MySwingWorker(Window owner, String msg) {
            this.owner = owner;
            this.msg = msg;
        }

        public MySwingWorker(Window owner) {
            this(owner, "Aguarde...");
        }

        @Override
        protected void process(List<String> chunks) {
            super.process(chunks);
            if (chunks.isEmpty()) {
                return;
            }
            Optional.ofNullable(popup).ifPresent(p -> p.setMessage(chunks.get(0)));
        }

        public void setupPopup(PopUpWorker popup) {
            this.popup = popup;
            addPropertyChangeListener(event -> {
                if ("state".equals(event.getPropertyName()) && SwingWorker.StateValue.DONE == event.getNewValue()) {
                    this.popup.setVisible(false);
                    this.popup.dispose();
                }
                if ("progress".equals(event.getPropertyName())) {
                    this.popup.setProgress(getProgress());
                }
            });
            this.popup.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentShown(ComponentEvent e) {
                    super.componentShown(e);
                    execute();
                }
            });
        }

        @Override
        protected void done() {
            super.done();
            if (!isCancelled()) {
                finish();
            } else {
                canceled();
            }
        }

        public void canceled() {
            //nothing
        }

        protected void showResult() {
            try {
                String s = get();
                switch (s.charAt(0)) {
                    case 't':
                        JOptionPane.showMessageDialog(owner, s.substring(1), "Mensagem", JOptionPane.PLAIN_MESSAGE);
                        break;
                    case 'f':
                        JOptionPane.showMessageDialog(owner, s.substring(1), "Mensagem", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } catch (Exception e) {
            }
        }

        protected String getResult() {
            try {
                return get().substring(1);
            } catch (Exception e) {
                return "";
            }
        }

        protected boolean isOk() {
            try {
                return get().charAt(0) == 't';
            } catch (Exception e) {
                return false;
            }
        }
    }

    public static class PopUpWaitingBuilder {

        private MySwingWorker worker = new MySwingWorker(null) {
            @Override
            protected String doInBackground() throws Exception {
                return null;
            }

            @Override
            public void finish() {
                //ignore
            }
        };
        private ImageIcon icon = DEFAULT_ICON;
        private boolean cancellable = false;
        private boolean undecorated = false;

        /**
         * MySwingWorker worker = null; ImageIcon icon = DEFAULT_ICON<br>
         * boolean cancellable = false<br>
         * boolean undecorated = false
         *
         * @return new Builder
         */
        public PopUpWorker create() {
            return new PopUpWorker(worker, icon, cancellable, undecorated);
        }

        public PopUpWaitingBuilder worker(MySwingWorker worker) {
            this.worker = worker;
            return this;
        }

        public PopUpWaitingBuilder icon(ImageIcon icon) {
            this.icon = icon;
            return this;
        }

        public PopUpWaitingBuilder icon(BufferedImage icon) {
            return icon(new ImageIcon(icon));
        }

        public PopUpWaitingBuilder cancellable(boolean cancellable) {
            this.cancellable = cancellable;
            return this;
        }

        public PopUpWaitingBuilder undecorated(boolean undecorated) {
            this.undecorated = undecorated;
            return this;
        }
    }
}

interface PopUpWorkerUtils {

    /**
     * Caixa de diálogo para confirmar uma ação
     *
     * @param c componente que chamou a ação
     * @param text texto exibido
     * @param title título da confirmação
     * @return {@code true} para confirmado. {@code false} o contrário.
     */
    default public boolean confirm(Component c, String text) {
        String title;
        if (Locale.getDefault().toString().equalsIgnoreCase("pt_BR")) {
            title = "Confirmar";
        } else {
            title = "Confirm";
        }
        return (showConfirmDialog(c, text, title, YES_NO_OPTION) == YES_OPTION);
    }
}
