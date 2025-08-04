﻿REM @echo off
REM ============================================================================
REM タイトル	：属性情報取得処理（責任者精算Crec）
REM 説明	：属性情報取得処理（責任者精算Crec）を実行する。
REM version 1.00 2016/11/14 J.Endo:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 属性情報取得処理（責任者精算Crec）
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012800
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
