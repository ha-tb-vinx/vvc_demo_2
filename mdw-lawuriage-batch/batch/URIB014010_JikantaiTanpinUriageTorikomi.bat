REM @echo off
REM ============================================================================
REM タイトル	：時間帯単品売上取込処理
REM 説明	：時間帯単品売上取込処理を実行する。
REM version 3.01 2013/10/28 T.Ooshiro:新規課題対応 №058
REM version 3.01 2014/02/03 Y.Tominaga:結合ﾃｽﾄNo.0112 時間帯関連バッチ_POS時間帯取込処理改善
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 時間帯単品売上取込処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB014010
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
