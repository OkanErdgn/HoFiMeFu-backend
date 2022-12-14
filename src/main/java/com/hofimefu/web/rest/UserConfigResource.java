package com.hofimefu.web.rest;

import com.hofimefu.domain.UserConfig;
import com.hofimefu.repository.UserConfigRepository;
import com.hofimefu.service.UserConfigService;
import com.hofimefu.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hofimefu.domain.UserConfig}.
 */
@RestController
@RequestMapping("/api")
public class UserConfigResource {

    private final Logger log = LoggerFactory.getLogger(UserConfigResource.class);

    private static final String ENTITY_NAME = "userConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserConfigService userConfigService;

    private final UserConfigRepository userConfigRepository;

    public UserConfigResource(UserConfigService userConfigService, UserConfigRepository userConfigRepository) {
        this.userConfigService = userConfigService;
        this.userConfigRepository = userConfigRepository;
    }

    /**
     * {@code POST  /user-configs} : Create a new userConfig.
     *
     * @param userConfig the userConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userConfig, or with status {@code 400 (Bad Request)} if the userConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-configs")
    public ResponseEntity<UserConfig> createUserConfig(@RequestBody UserConfig userConfig) throws URISyntaxException {
        log.debug("REST request to save UserConfig : {}", userConfig);
        if (userConfig.getId() != null) {
            throw new BadRequestAlertException("A new userConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserConfig result = userConfigService.save(userConfig);
        return ResponseEntity
            .created(new URI("/api/user-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-configs/:id} : Updates an existing userConfig.
     *
     * @param id the id of the userConfig to save.
     * @param userConfig the userConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userConfig,
     * or with status {@code 400 (Bad Request)} if the userConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-configs/{id}")
    public ResponseEntity<UserConfig> updateUserConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserConfig userConfig
    ) throws URISyntaxException {
        log.debug("REST request to update UserConfig : {}, {}", id, userConfig);
        if (userConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserConfig result = userConfigService.update(userConfig);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userConfig.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-configs/:id} : Partial updates given fields of an existing userConfig, field will ignore if it is null
     *
     * @param id the id of the userConfig to save.
     * @param userConfig the userConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userConfig,
     * or with status {@code 400 (Bad Request)} if the userConfig is not valid,
     * or with status {@code 404 (Not Found)} if the userConfig is not found,
     * or with status {@code 500 (Internal Server Error)} if the userConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-configs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserConfig> partialUpdateUserConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserConfig userConfig
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserConfig partially : {}, {}", id, userConfig);
        if (userConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserConfig> result = userConfigService.partialUpdate(userConfig);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userConfig.getId().toString())
        );
    }

    /**
     * {@code GET  /user-configs} : get all the userConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userConfigs in body.
     */
    @GetMapping("/user-configs")
    public List<UserConfig> getAllUserConfigs() {
        log.debug("REST request to get all UserConfigs");
        return userConfigService.findAll();
    }

    /**
     * {@code GET  /user-configs/:id} : get the "id" userConfig.
     *
     * @param id the id of the userConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-configs/{id}")
    public ResponseEntity<UserConfig> getUserConfig(@PathVariable Long id) {
        log.debug("REST request to get UserConfig : {}", id);
        Optional<UserConfig> userConfig = userConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userConfig);
    }

    /**
     * {@code DELETE  /user-configs/:id} : delete the "id" userConfig.
     *
     * @param id the id of the userConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-configs/{id}")
    public ResponseEntity<Void> deleteUserConfig(@PathVariable Long id) {
        log.debug("REST request to delete UserConfig : {}", id);
        userConfigService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
