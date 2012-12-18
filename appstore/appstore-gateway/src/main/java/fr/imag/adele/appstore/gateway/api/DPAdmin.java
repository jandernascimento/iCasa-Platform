package fr.imag.adele.appstore.gateway.api;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.StaticServiceProperty;
import org.osgi.service.deploymentadmin.DeploymentAdmin;
import org.osgi.service.deploymentadmin.DeploymentException;
import org.osgi.service.deploymentadmin.DeploymentPackage;
import org.ow2.chameleon.json.JSONService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import fr.imag.adele.appstore.gateway.util.DPResourceManipulator;


@Component(name="deployment-admin-rest-api")
@Instantiate(name="deployment-admin-rest-api-0")
@Provides(specifications={DPAdmin.class}, properties= {
		@StaticServiceProperty(name="appstore.exporter.protocol", type="java.lang.String", value="jax-rs")})
@Path(value="/")
public class DPAdmin {

	@Requires
	private JSONService jsonservice; //JsonService, in order to parse request and build responses
	
	@Requires
	private DeploymentAdmin dadmin;
	
	protected static Logger coreLogger = LoggerFactory.getLogger("appstore.logger");


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/applications/")
	public Response getInstalledApplications() {
		DeploymentPackage[] installed = dadmin.listDeploymentPackages();
		List<Map<String, String>> installedInfo = DPResourceManipulator.toList(installed);
		String result = jsonservice.toJSON(Collections.singletonMap("installedPackages", installedInfo));
		return Response.ok(result).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/applications/{name}")
	public Response getApplication(@PathParam("name")String name) {
		DeploymentPackage ipackage = dadmin.getDeploymentPackage(name);
		if (ipackage == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		String result = jsonservice.toJSON(DPResourceManipulator.toMap(ipackage));
		return Response.ok(result).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/applications/")
	public Response installApplication(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		DeploymentPackage newPackage = null;
		try {
			newPackage = dadmin.installDeploymentPackage(uploadedInputStream);
		} catch (DeploymentException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok(jsonservice.toJSON(DPResourceManipulator.toMap(newPackage))).build();
	}
	
	@DELETE
	@Path("/applications/{name}")
	public Response removeApplication(@PathParam("name")String name) {
		DeploymentPackage ipackage = dadmin.getDeploymentPackage(name);
		if (ipackage == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		try {
			ipackage.uninstallForced();
		} catch (DeploymentException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		String result = jsonservice.toJSON(DPResourceManipulator.toMap(ipackage));
		return Response.ok(result).build();
	}
	
}
