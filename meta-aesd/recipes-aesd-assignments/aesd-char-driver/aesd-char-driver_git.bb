# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "gitsm://github.com/cu-ecen-aeld/assignments-3-and-later-FilipOwsiany.git;protocol=https;branch=main"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "d39fe806868dc6e81cce4c49f229eafd73361925"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit module update-rc.d

SRC_URI += "file://S98aesdchardriver"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98aesdchardriver"

FILES:${PN} += "${sbindir}/aesdchar_load"
FILES:${PN} += "${sbindir}/aesdchar_unload"
FILES:${PN} += "S98aesdchardriver"
FILES:${PN} += "${sysconfdir}"
FILES:${PN} += "${sysconfdir}/init.d"


do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
    # Installing module
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra

    # Installing module stop and start script
    install -d ${D}/usr/sbin
    install -m 0755 ${S}/aesdchar_load ${D}/usr/sbin
    install -m 0755 ${S}/aesdchar_unload ${D}/usr/sbin

    # Installing init script for misc-module    
    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/S98aesdchardriver ${D}${sysconfdir}/init.d/
}

