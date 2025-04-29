package cl.clarochile.osbservicios.PlataformaPagoNotificarSAF;

public class PlataformaPagonotificarDevolucionSAFProxy
		implements
		cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.PlataformaPagonotificarDevolucionSAF {
	private String _endpoint = null;
	private cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.PlataformaPagonotificarDevolucionSAF plataformaPagonotificarDevolucionSAF = null;

	public PlataformaPagonotificarDevolucionSAFProxy() {
		_initPlataformaPagonotificarDevolucionSAF_PlataformaPagonotificarDevolucionSAFSOAPImplProxy();
	}

	public PlataformaPagonotificarDevolucionSAFProxy(String endpoint) {
		_endpoint = endpoint;
		_initPlataformaPagonotificarDevolucionSAF_PlataformaPagonotificarDevolucionSAFSOAPImplProxy();
	}

	private void _initPlataformaPagonotificarDevolucionSAF_PlataformaPagonotificarDevolucionSAFSOAPImplProxy() {
		try {
			plataformaPagonotificarDevolucionSAF = (new cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.NotificarDevolucionSAFServiceLocator())
					.getNotificarDevolucionSAFPort();
			if (plataformaPagonotificarDevolucionSAF != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) plataformaPagonotificarDevolucionSAF)
							._setProperty(
									"javax.xml.rpc.service.endpoint.address",
									_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) plataformaPagonotificarDevolucionSAF)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (plataformaPagonotificarDevolucionSAF != null)
			((javax.xml.rpc.Stub) plataformaPagonotificarDevolucionSAF)
					._setProperty("javax.xml.rpc.service.endpoint.address",
							_endpoint);

	}

	public cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.PlataformaPagonotificarDevolucionSAF getServer() {
		if (plataformaPagonotificarDevolucionSAF == null)
			_initPlataformaPagonotificarDevolucionSAF_PlataformaPagonotificarDevolucionSAFSOAPImplProxy();
		return plataformaPagonotificarDevolucionSAF;
	}

	public cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.PlataformaPagonotificarDevolucionSAF getPlataformaPagonotificarDevolucionSAF_PlataformaPagonotificarDevolucionSAFSOAPImpl() {
		if (plataformaPagonotificarDevolucionSAF == null)
			_initPlataformaPagonotificarDevolucionSAF_PlataformaPagonotificarDevolucionSAFSOAPImplProxy();
		return plataformaPagonotificarDevolucionSAF;
	}

	public void devolverSaldoFavor(
			cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.DevuelveSaldoFavorPeticionType arg0,
			cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.HeaderRequest arg1,
			cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.DevuelveSaldoFavorRespuestaTypeHolder arg2,
			cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.DatosHeaderResponseHolder arg3)
			throws java.rmi.RemoteException {
		if (plataformaPagonotificarDevolucionSAF == null)
			_initPlataformaPagonotificarDevolucionSAF_PlataformaPagonotificarDevolucionSAFSOAPImplProxy();
		plataformaPagonotificarDevolucionSAF.devolverSaldoFavor(arg0, arg1,
				arg2, arg3);
	}

}