package hackathon.polata.getir.core;

/**
 * Created by polata on 19/02/2016.
 */
public interface BaseController {

    /**
     * Attach controller to fragment.
     *
     * @param fragment fragment
     */
    void attach(BaseFragment fragment);

    /**
     * Detach controller from fragment.
     *
     * @param fragment fragment
     */
    void detach(BaseFragment fragment);
}
