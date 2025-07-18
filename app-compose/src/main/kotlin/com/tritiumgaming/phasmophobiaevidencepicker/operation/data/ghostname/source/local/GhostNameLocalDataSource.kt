package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostname.source.local

import android.content.Context
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostname.dto.GhostNameDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostname.source.GhostNameDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.dto.MissionDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.MissionDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.mappers.GhostNameResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.model.GhostName
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.mappers.MissionResources.MissionContent

class GhostNameLocalDataSource(
    private val applicationContext: Context
): GhostNameDataSource {

    val nameResources
        get() = listOf(
            GhostNameResourceDto(
                name = GhostNameResources.Name.ALEX,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.AMIT,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ANDREW,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ANTHONY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BENJAMIN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BILLY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BRADLEY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BRENDEN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BRIAN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CARLOS,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CHARLES,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CHRISTOPHER,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.COREY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.DANIEL,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.DAVE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.DAVID,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.DONALD,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.EDWARD,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ERIC,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.GARY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.GEORGE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.GRANT,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.GREGORY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.HAROLD,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.HUGO,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JACK,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JAMES,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JAN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JASON,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JAY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JERRY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JOHN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JOSEPH,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JUSTIN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.KEITH,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.KENNETH,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.KENNY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.KEVIN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.KYLE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.LARRY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.LESLIE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.LUKE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MARK,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MICHAEL,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.PAUL,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.PETER,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.RAYMOND,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.RICHARD,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ROBERT,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ROBIN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.RONALD,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.RUSSELL,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.STEVEN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.THOMAS,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.TED,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.TIM,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.WALTER,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.WILLIAM,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ANN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.APRIL,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BARBARA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BECKY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BETTY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BORRIS,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CARLA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CAROL,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CATHERINE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CATIANA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CORA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.DONNA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.DORIS,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.DOROTHY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.EDIE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ELIZABETH,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ELLA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ELLEN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.EMILY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.EMMA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.EVA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.GEORGIA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.GLORIA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.HEATHER,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.HELEN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.HOLLY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JANE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JAZZ,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JENNIFER,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JENNISE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JESSICA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JO,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JOSEFINE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JUDY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JULIE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.KAREN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.KATE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.KELLY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.KIM,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.LESLIE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.LINDA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.LISA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.LIVY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.LORI,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.LUCY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MARCIA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MARGARET,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MARIA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MARIE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MARY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MEGAN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MICHELLE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.NANCY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.NELLIE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.PATRICIA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ROBIN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ROSE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.RUTH,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SANDRA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SARAH,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SHANNON,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SHARNE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SHELLY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SOPHIE,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.STACEY,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SUSAN,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.TRICIA,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.FEMALE
            ),
            GhostNameResourceDto(
                    name = GhostNameResources.Name.ALEXANDER,
            priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ANDERSON,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BAILEY,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BAKER,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BARBER,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BARTON,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BELLFIELD,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BIRCH,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BISHOP,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BOWEN,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BROCK,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BROOKS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BROWN,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.BUCKLEY,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CAREY,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CARTER,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CLARK,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CLARKE,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CORDERO,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.CORRIGAN,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.DAVIS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.DEXTER,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.DIXON,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.DOE,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.DOUGLAS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.DYER,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ELLIOTT,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.EMMETT,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.EVERLY,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.GACY,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.GARCIA,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.GAYTON,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.HALSTEAD,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.HANS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.HARRIS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.HILL,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.HOLLAND,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.HOLMES,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.HUNTLEY,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JACKSON,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JOHNSON,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.JONES,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.KEMPER,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.KNIGHT,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.KRAFT,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.KRAY,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.LANCASTER,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.LAVENDER,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.LEE,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.LEWIS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MANSON,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MARSH,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MARTIN,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MARTINEZ,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MAUDSLEY,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MILLER,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MILLS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MOORE,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.MYERS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.NILSEN,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.NORRIS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.PETTIT,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.PHILLIPS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.RAMIREZ,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.RHOADES,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ROBERTS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ROBINSON,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ROOK,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.ROSWELL,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SCHELIN,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SHAWCROSS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SHERMAN,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SHIPMAN,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SKINNER,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SMITH,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.STEVENS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.STRAFFEN,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.SWEENEY,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.TAYLOR,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.THOMAS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.THOMPSON,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.TODD,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.WALKER,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.WATTS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.WEST,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.WHITE,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.WILLIAMS,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.WILSON,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.WINTER,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.WRIGHT,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
            GhostNameResourceDto(
                name = GhostNameResources.Name.YOUNG,
                priority = GhostName.NamePriority.SURNAME,
                gender = GhostName.Gender.UNSPECIFIED
            ),
        )

    override fun get(): Result<List<GhostNameDto>> {
        return Result.success(nameResources.map { it.toGhostNameDto() })
    }

}

fun GhostNameResourceDto.toGhostNameDto() = GhostNameDto(
    name = name,
    priority = priority,
    gender = gender
)

data class GhostNameResourceDto(
    val name: GhostNameResources.Name,
    val priority: GhostName.NamePriority,
    val gender: GhostName.Gender
)