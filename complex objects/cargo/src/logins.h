/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
 
#include <stdint.h>

struct login;

struct login* login_new(const char* username, const char* password);
const size_t validate_login(const char* username, const char* password);
void login_destroy(struct login* data);

// Accessors for Login properties
const size_t login_get_id(const struct login* data);
const char* login_get_username(const struct login* data);
const char* login_get_password(const struct login* data);
const char* login_get_guid(const struct login* data);
const void login_set_guid(struct login* data, char* guid);
const size_t login_get_time_created(const struct login* data);
const size_t login_get_time_last_used(const struct login* data);
const size_t login_get_time_password_changed(const struct login* data);
const size_t login_get_times_used(const struct login* data);
const size_t login_is_valid(const struct login* data);
