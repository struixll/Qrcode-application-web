package discussion;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.krysalis.barcode4j.impl.datamatrix.DataMatrixBean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;


@WebServlet("/SumServlet")
public class SumServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SumServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String libelle = request.getParameter("libelle");
        String message = "Le code du formulaire est " + code + ". Le libellé est " + libelle + ".";


        DataMatrixBean bean = new DataMatrixBean();
        final int dpi = 600;

        try {
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

            // Inclure le message dans le code-barre
            bean.generateBarcode(canvas, message);
            canvas.finish();

            response.setContentType("image/png");
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(canvas.getBufferedImage(), "png", out);
            out.close();
            System.out.println("Code-barre généré avec succès et renvoyé au client.");
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la génération du code-barre ou de l'écriture de l'image.");
        }
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

