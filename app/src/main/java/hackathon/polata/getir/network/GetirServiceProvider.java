package hackathon.polata.getir.network;

/**
 * Created by polata on 20/02/2016.
 */
public final class GetirServiceProvider {

    private static GetirService getirService;

    /**
     * Constructor.
     */
    private GetirServiceProvider() {
    }

    /**
     * Get singleton service instance.
     *
     * @return instance
     */
    public static GetirService getService() {
        if (getirService == null) {
            getirService = GetirServiceGenerator.createService(GetirService.class);
        }

        return getirService;
    }
}
