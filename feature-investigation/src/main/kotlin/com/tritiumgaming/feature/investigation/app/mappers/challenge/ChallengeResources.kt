package com.tritiumgaming.feature.investigation.app.mappers.challenge

import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources

fun ChallengeResources.ChallengeTitle.toStringResource() =
    when(this) {
        ChallengeResources.ChallengeTitle.LIGHTS_OUT -> R.string.challenge_lights_out_title
        ChallengeResources.ChallengeTitle.SPEED_DEMONS -> R.string.challenge_speed_demons_title
        ChallengeResources.ChallengeTitle.DETECTIVES_ONLY -> R.string.challenge_detectives_only_title
        ChallengeResources.ChallengeTitle.HIDE_AND_SEEK_SEEKER -> R.string.challenge_hide_and_seek_seeker_title
        ChallengeResources.ChallengeTitle.HIDE_AND_SEEK_HIDE -> R.string.challenge_hide_and_seek_hide_title
        ChallengeResources.ChallengeTitle.FROSTBITTEN -> R.string.challenge_frostbitten_title
        ChallengeResources.ChallengeTitle.DO_AS_I_COMMAND -> R.string.challenge_do_as_i_command_title
        ChallengeResources.ChallengeTitle.TORTOISE_AND_THE_HARE_HARE -> R.string.challenge_tortoise_and_the_hare_hare_title
        ChallengeResources.ChallengeTitle.TORTOISE_AND_THE_HARE_TORTOISE -> R.string.challenge_tortoise_and_the_hare_tortoise_title
        ChallengeResources.ChallengeTitle.GOTTA_GO_FAST -> R.string.challenge_gotta_go_fast_title
        ChallengeResources.ChallengeTitle.SANITY_SURVIVAL -> R.string.challenge_sanity_survival_title
        ChallengeResources.ChallengeTitle.SPEEDRUN -> R.string.challenge_speedrun_title
        ChallengeResources.ChallengeTitle.SURVIVAL_OF_THE_FITTEST -> R.string.challenge_survival_of_the_fittest_title
        ChallengeResources.ChallengeTitle.PRIMITIVE -> R.string.challenge_primitive_title
        ChallengeResources.ChallengeTitle.VULNERABLE -> R.string.challenge_vulnerable_title
        ChallengeResources.ChallengeTitle.MISSED_DELIVERY -> R.string.challenge_missed_delivery_title
        ChallengeResources.ChallengeTitle.AUDIO_ONLY -> R.string.challenge_audio_only_title
        ChallengeResources.ChallengeTitle.TECHNOPHILIA -> R.string.challenge_technophilia_title
        ChallengeResources.ChallengeTitle.NO_EVIDENCE -> R.string.challenge_no_evidence_title
        ChallengeResources.ChallengeTitle.THE_APOCALYPSE_DRAWS_NEAR -> R.string.challenge_the_apocalypse_draws_near_title
        ChallengeResources.ChallengeTitle.SLOW_AND_STEADY -> R.string.challenge_slow_and_steady_title
        ChallengeResources.ChallengeTitle.PARANORMAL_PAPARAZZI -> R.string.challenge_paranormal_paparazzi_title
        ChallengeResources.ChallengeTitle.HIDE_AND_SEEK_EXTREME -> R.string.challenge_hide_and_seek_extreme_title
        ChallengeResources.ChallengeTitle.GLOW_IN_THE_DARK -> R.string.challenge_glow_in_the_dark_title
        ChallengeResources.ChallengeTitle.DEJA_VU -> R.string.challenge_deja_vu_title
        ChallengeResources.ChallengeTitle.TAG_YOURE_IT -> R.string.challenge_tag_youre_it_title
    }

fun ChallengeResources.ChallengeDescription.toStringResource() =
    when(this) {
        ChallengeResources.ChallengeDescription.LIGHTS_OUT -> R.string.challenge_lights_out_description
        ChallengeResources.ChallengeDescription.SPEED_DEMONS -> R.string.challenge_speed_demons_description
        ChallengeResources.ChallengeDescription.DETECTIVES_ONLY -> R.string.challenge_detectives_only_description
        ChallengeResources.ChallengeDescription.HIDE_AND_SEEK_SEEKER -> R.string.challenge_hide_and_seek_seeker_description
        ChallengeResources.ChallengeDescription.HIDE_AND_SEEK_HIDE -> R.string.challenge_hide_and_seek_hide_description
        ChallengeResources.ChallengeDescription.FROSTBITTEN -> R.string.challenge_frostbitten_description
        ChallengeResources.ChallengeDescription.DO_AS_I_COMMAND -> R.string.challenge_do_as_i_command_description
        ChallengeResources.ChallengeDescription.TORTOISE_AND_THE_HARE_HARE -> R.string.challenge_tortoise_and_the_hare_hare_description
        ChallengeResources.ChallengeDescription.TORTOISE_AND_THE_HARE_TORTOISE -> R.string.challenge_tortoise_and_the_hare_tortoise_description
        ChallengeResources.ChallengeDescription.GOTTA_GO_FAST -> R.string.challenge_gotta_go_fast_description
        ChallengeResources.ChallengeDescription.SANITY_SURVIVAL -> R.string.challenge_sanity_survival_description
        ChallengeResources.ChallengeDescription.SPEEDRUN -> R.string.challenge_speedrun_description
        ChallengeResources.ChallengeDescription.SURVIVAL_OF_THE_FITTEST -> R.string.challenge_survival_of_the_fittest_description
        ChallengeResources.ChallengeDescription.PRIMITIVE -> R.string.challenge_primitive_description
        ChallengeResources.ChallengeDescription.VULNERABLE -> R.string.challenge_vulnerable_description
        ChallengeResources.ChallengeDescription.MISSED_DELIVERY -> R.string.challenge_missed_delivery_description
        ChallengeResources.ChallengeDescription.AUDIO_ONLY -> R.string.challenge_audio_only_description
        ChallengeResources.ChallengeDescription.TECHNOPHILIA -> R.string.challenge_technophilia_description
        ChallengeResources.ChallengeDescription.NO_EVIDENCE -> R.string.challenge_no_evidence_description
        ChallengeResources.ChallengeDescription.THE_APOCALYPSE_DRAWS_NEAR -> R.string.challenge_the_apocalypse_draws_near_description
        ChallengeResources.ChallengeDescription.SLOW_AND_STEADY -> R.string.challenge_slow_and_steady_description
        ChallengeResources.ChallengeDescription.PARANORMAL_PAPARAZZI -> R.string.challenge_paranormal_paparazzi_description
        ChallengeResources.ChallengeDescription.HIDE_AND_SEEK_EXTREME -> R.string.challenge_hide_and_seek_extreme_description
        ChallengeResources.ChallengeDescription.GLOW_IN_THE_DARK -> R.string.challenge_glow_in_the_dark_description
        ChallengeResources.ChallengeDescription.DEJA_VU -> R.string.challenge_deja_vu_description
        ChallengeResources.ChallengeDescription.TAG_YOURE_IT -> R.string.challenge_tag_youre_it_description
    }
