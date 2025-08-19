package tech.hariprasath.batchdemo.roles;

import lombok.Getter;
import java.util.Set;

@Getter
public enum Role {
    USER(Set.of(Permissions.BATCH_DATA_READ)),
    ADMIN(Set.of(Permissions.BATCH_DATA_READ,
            Permissions.BATCH_DATA_WRITE,
            Permissions.BATCH_DATA_DELETE,
            Permissions.BATCH_DATA_UPDATE)
    ),
    ;

    private final Set<Permissions> permissions;

    Role(Set<Permissions> permissions) {
        this.permissions = permissions;
    }
}
